package de.stinner.anmeldetool.domain.nami.service.client;

import de.stinner.anmeldetool.application.rest.NamiApiEndpoints;
import de.stinner.anmeldetool.domain.nami.service.client.models.NamiMember;
import de.stinner.anmeldetool.domain.nami.service.client.models.NamiMembersWrapper;
import de.stinner.anmeldetool.domain.nami.service.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

@Slf4j
public class NamiClient implements AutoCloseable {

    private final String namiUri;
    private final CookieStore cookieStore = new BasicCookieStore();

    private final CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCookieStore(cookieStore)
            .setRedirectStrategy(DefaultRedirectStrategy.INSTANCE)
            .build();

    private final RestTemplate namiRestTemplate = buildNamiRestTemplate();

    public NamiClient(String namiUri, String username, String password) {
        this.namiUri = namiUri;
        this.login(username, password);
    }

    private void login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("redirectTo", "./pages/loggedin.jsp");
        map.add("Login", "API");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> loginResponse = namiRestTemplate.exchange(
                namiUri + NamiApiEndpoints.LOGIN,
                HttpMethod.POST,
                entity,
                String.class
        );

        verifyLogin(loginResponse);
    }

    private void verifyLogin(ResponseEntity<String> loginResponse) {
        if (!loginResponse.getStatusCode().is2xxSuccessful()) {
            throw new NamiException();
        }
        if (cookieStore.getCookies().stream().noneMatch(c -> c.getName().equals("JSESSIONID"))) {
            throw new NamiException();
        }
        String body = loginResponse.getBody();
        if (null == body || body.contains(NamiApiEndpoints.LOGIN)) {
            throw new NamiLoginFailedException();
        }
    }

    public Collection<NamiMember> getAllMembersOfGrouping(String groupingId) {
        ResponseEntity<NamiMembersWrapper> responseEntity = namiRestTemplate.exchange(
                namiUri + NamiApiEndpoints.allMembersOfGrouping(groupingId),
                HttpMethod.GET,
                null,
                NamiMembersWrapper.class
        );

        return verifyGetAllMembersOfGrouping(responseEntity);
    }

    private Collection<NamiMember> verifyGetAllMembersOfGrouping(ResponseEntity<NamiMembersWrapper> responseEntity) {
        NamiMembersWrapper namiMembersWrapper = responseEntity.getBody();
        if (!responseEntity.getStatusCode().is2xxSuccessful() || null == namiMembersWrapper) {
            throw new NamiException();
        }
        switch (namiMembersWrapper.getResponseType()) {
            case OK -> {
                return namiMembersWrapper.getData();
            }
            case ERROR -> throw new NamiSessionExpiredException();
            case EXCEPTION -> throw new NamiAccessViolationException();
            default -> throw new NamiException();
        }
    }

    private RestTemplate buildNamiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(@NonNull ClientHttpResponse response) {
                try {
                    return super.hasError(response);
                } catch (HttpStatusCodeException exception) {
                    throw new NamiException();
                } catch (IOException exception) {
                    throw new NamiUnavailableException();
                }
            }
        });

        return restTemplate;
    }

    @Override
    public void close() {
        logout();
        try {
            httpClient.close();
        } catch (Exception e) {
            throw new NamiException(e);
        }
    }

    private void logout() {
        ResponseEntity<Void> logoutResponse = namiRestTemplate.exchange(
                namiUri + NamiApiEndpoints.LOGOUT,
                HttpMethod.GET,
                null,
                Void.class
        );

        verifyLogout(logoutResponse);
    }

    private void verifyLogout(ResponseEntity<?> logoutResponse) {
        cookieStore.clearExpired(Instant.now());
        if (!logoutResponse.getStatusCode().is2xxSuccessful()) {
            log.warn("Nami logout not successful. Returned " + logoutResponse.getStatusCode() + ".");
        }
        if (cookieStore.getCookies().stream().anyMatch(c -> c.getName().equals("JSESSIONID"))) {
            log.warn("Nami logout not successful. Cookie was not expired.");
        }
        // Clear cookie store to be on the safe side
        cookieStore.clear();
    }
}