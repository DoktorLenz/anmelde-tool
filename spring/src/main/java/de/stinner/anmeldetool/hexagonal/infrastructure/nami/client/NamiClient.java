package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.exceptions.NamiAccessViolationException;
import de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.exceptions.NamiException;
import de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.exceptions.NamiLoginFailedException;
import de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.exceptions.NamiSessionExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    private final NamiRestTemplate namiRestTemplate = new NamiRestTemplate(httpClient);

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
        map.add("Login", "API");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<NamiLoginResponse> loginResponse;

        loginResponse = namiRestTemplate.exchange(
                namiUri + NamiApiEndpoints.LOGIN,
                HttpMethod.POST,
                entity,
                NamiLoginResponse.class
        );

        verifyLogin(loginResponse);
    }

    private void verifyLogin(ResponseEntity<NamiLoginResponse> responseEntity) {
        HttpStatus statusCode = HttpStatus.resolve(responseEntity.getStatusCode().value());

        if (statusCode == HttpStatus.NO_CONTENT) {
            throw new NamiException("Session activation error");
        }

        if (statusCode != HttpStatus.OK) {
            throw new NamiException("Unexpected status code " + statusCode);
        }

        if (cookieStore.getCookies().stream().noneMatch(c -> c.getName().equals("JSESSIONID"))) {
            throw new NamiException("No session cookie received");
        }

        NamiLoginResponse loginResponse = responseEntity.getBody();

        if (loginResponse == null) {
            throw new NamiException("Empty body");
        }

        if (loginResponse.getStatusCode() == 3000) {
            throw new NamiLoginFailedException(loginResponse.getStatusMessage());
        }
    }

    public Collection<ClientNamiMember> getAllMembersOfGrouping(String groupingId) {
        ResponseEntity<NamiMembersWrapper> responseEntity = namiRestTemplate.exchange(
                namiUri + NamiApiEndpoints.allMembersOfGrouping(groupingId),
                HttpMethod.GET,
                null,
                NamiMembersWrapper.class
        );

        return verifyGetAllMembersOfGrouping(responseEntity);
    }

    private Collection<ClientNamiMember> verifyGetAllMembersOfGrouping(ResponseEntity<NamiMembersWrapper> responseEntity) {
        NamiMembersWrapper namiMembersWrapper = responseEntity.getBody();
        if (null == namiMembersWrapper) {
            throw new NamiException("Empty body");
        }
        String responseType = namiMembersWrapper.getResponseType();
        if ("OK".equals(responseType)) {
            return namiMembersWrapper.getData();
        } else if ("ERROR".equals(responseType)) {
            throw new NamiSessionExpiredException();
        } else if ("EXCEPTION".equals(responseType)) {
            throw new NamiAccessViolationException();
        } else {
            throw new NamiException("Unhandled responseType: " + responseType +
                    ". Message: " + namiMembersWrapper.getMessage());
        }
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
        namiRestTemplate.exchange(
                namiUri + NamiApiEndpoints.LOGOUT,
                HttpMethod.GET,
                null,
                Void.class
        );

        verifyLogout();
    }

    private void verifyLogout() {
        cookieStore.clearExpired(Instant.now());
        if (cookieStore.getCookies().stream().anyMatch(c -> c.getName().equals("JSESSIONID"))) {
            log.warn("Nami logout not successful. Cookie was not expired.");
        }
        // Clear cookie store to be on the safe side
        cookieStore.clear();
    }
}