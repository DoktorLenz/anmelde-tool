package de.stinner.anmeldetool.domain.nami.service;

import de.stinner.anmeldetool.domain.nami.service.exceptions.NamiAccessViolationException;
import de.stinner.anmeldetool.domain.nami.service.exceptions.NamiException;
import de.stinner.anmeldetool.domain.nami.service.exceptions.NamiLoginFailedException;
import de.stinner.anmeldetool.domain.nami.service.models.NamiMember;
import de.stinner.anmeldetool.domain.nami.service.models.NamiMembersWrapper;
import de.stinner.anmeldetool.domain.usermanagement.namimembers.service.NamiMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequiredArgsConstructor
@Service
public class NamiService {

    private final RestTemplate namiApiRestTemplate;
    private final NamiMemberService namiMemberService;


    public void namiImport(String username, String password, String groupId) {
        HttpCookie jsessionCookie = namiLogin(username, password);
        List<NamiMember> namiMembers = getAllNamiMembers(jsessionCookie, groupId);
        namiMemberService.importNamiMembers(namiMembers);
    }


    /**
     * Attempts a login for the given user.
     *
     * @param username Nami Username
     * @param password Nami Password for Username
     * @return JSession-Cookie when login was successful
     */
    private HttpCookie namiLogin(String username, String password) {
        String loginActivationLocation = sessionStartup(username, password);
        return activateLogin(loginActivationLocation);
    }

    private List<NamiMember> getAllNamiMembers(HttpCookie sessionCookie, String groupId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionCookie.toString());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<NamiMembersWrapper> responseEntity = namiApiRestTemplate.exchange(
                NamiApiEndpoints.allMembers(groupId),
                HttpMethod.GET,
                entity,
                NamiMembersWrapper.class
        );

        return handleAllMembersResponse(responseEntity);
    }

    private String sessionStartup(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("redirectTo", "./pages/loggedin.jsp");
        map.add("Login", "API");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Void> responseEntity = namiApiRestTemplate.exchange(
                NamiApiEndpoints.LOGIN,
                HttpMethod.POST,
                entity,
                Void.class
        );

        return handleSessionStartupResponse(responseEntity);
    }

    private HttpCookie activateLogin(String location) {
        ResponseEntity<Void> responseEntity = namiApiRestTemplate.exchange(
                location,
                HttpMethod.GET,
                null,
                Void.class
        );

        return handleActivationResponse(responseEntity);
    }

    private List<NamiMember> handleAllMembersResponse(ResponseEntity<NamiMembersWrapper> responseEntity) {
        NamiMembersWrapper namiMembersWrapper = responseEntity.getBody();
        if (namiMembersWrapper == null) {
            throw new NamiException();
        }
        if (namiMembersWrapper.isSuccess()) {
            return namiMembersWrapper.getData();
        }
        throw new NamiAccessViolationException(namiMembersWrapper.getMessage());
    }

    private String handleSessionStartupResponse(ResponseEntity<Void> responseEntity) {
        if (responseEntity.getStatusCode().is3xxRedirection()) {
            return responseEntity.getHeaders().getFirst(HttpHeaders.LOCATION);
        } else {
            throw new NamiLoginFailedException();
        }
    }

    private HttpCookie handleActivationResponse(ResponseEntity<Void> responseEntity) {
        String cookieHeader = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        if (cookieHeader != null && !cookieHeader.isEmpty()) {
            int jSessionIDIndex = cookieHeader.indexOf("JSESSIONID=");
            int semicolonIndex = cookieHeader.indexOf(";", jSessionIDIndex);
            String jsessionId = cookieHeader.substring(jSessionIDIndex + 11, semicolonIndex);
            return new HttpCookie("JSESSIONID", jsessionId);
        }
        throw new NamiLoginFailedException();
    }
}
