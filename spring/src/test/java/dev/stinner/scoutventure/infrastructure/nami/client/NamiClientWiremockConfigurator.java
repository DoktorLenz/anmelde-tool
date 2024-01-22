package dev.stinner.scoutventure.infrastructure.nami.client;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.function.Function;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class NamiClientWiremockConfigurator {
    public static final NamiClientWiremockConfigurator INSTANCE = new NamiClientWiremockConfigurator();

    private static final String activateLoginLocation = "/ica/rest/nami/auth/loginActivatin/someid/HUMAN?redirectTo=%2Fpages%2Floggedin.jsp";
    private static final String jsessionId = "NAMI_SESSION_ID";
    private static final String cookie = "JSESSIONID=" + jsessionId + "; Path=/ica;";
    private static final String cookieWithExpiration = cookie + " expires=Thu, 01 Jan 1970 00:00:00 GMT;";
    private static final ObjectWriter jsonObjectWriter = JsonMapper.builder()
            .findAndAddModules().build()
            .writer().withDefaultPrettyPrinter();
    private final NamiClientWiremockSuccessConfigurator SUCCESS_INSTANCE =
            new NamiClientWiremockSuccessConfigurator();
    private final NamiClientWiremockFailureConfigurator FAILURE_INSTANCE =
            new NamiClientWiremockFailureConfigurator();

    private NamiClientWiremockConfigurator() {
    }

    public NamiClientWiremockConfigurator failure(
            Function<NamiClientWiremockFailureConfigurator, NamiClientWiremockFailureConfigurator> failureConfiguration
    ) {
        failureConfiguration.apply(FAILURE_INSTANCE);
        return this;
    }

    public void allSuccessful(String username, String password, String groupingId) {
        INSTANCE.success(success -> success
                .login(username, password)
                .activateLogin()
                .getAllNamiMembers(groupingId)
                .logout()
        );
    }

    public NamiClientWiremockConfigurator success(
            Function<NamiClientWiremockSuccessConfigurator, NamiClientWiremockSuccessConfigurator> successConfiguration
    ) {
        successConfiguration.apply(SUCCESS_INSTANCE);
        return this;
    }

    public static class NamiClientWiremockSuccessConfigurator {
        public NamiClientWiremockSuccessConfigurator login(String username, String password) {
            stubFor(post(urlEqualTo(NamiApiEndpoints.LOGIN))
                    .withHeader(
                            HttpHeaders.CONTENT_TYPE,
                            containing(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    )
                    .withRequestBody(binaryEqualTo(
                            ("username=" + username +
                                    "&password=" + password +
                                    "&Login=API")
                                    .getBytes()
                    ))
                    .willReturn(
                            aResponse()
                                    .withStatus(302)
                                    .withHeader(
                                            HttpHeaders.LOCATION,
                                            "http://localhost:43500" + activateLoginLocation
                                    )
                    )
            );

            return this;
        }

        @SneakyThrows
        public NamiClientWiremockSuccessConfigurator activateLogin() {
            NamiLoginResponse loginResponse = new NamiLoginResponse();
            loginResponse.setStatusCode(0);
            loginResponse.setStatusMessage("");

            stubFor(get(urlEqualTo(activateLoginLocation))
                    .willReturn(
                            aResponse()
                                    .withStatus(200)
                                    .withHeader(
                                            HttpHeaders.SET_COOKIE,
                                            cookie
                                    )
                                    .withBody(jsonObjectWriter.writeValueAsString(
                                            loginResponse
                                    ))
                    )
            );

            return this;
        }

        @SneakyThrows
        public NamiClientWiremockSuccessConfigurator getAllNamiMembers(String groupingId) {
            stubFor(get(urlEqualTo(NamiApiEndpoints.allMembersOfGrouping(groupingId)))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            ok()
                                    .withBody(jsonObjectWriter.writeValueAsString(
                                            NamiTestData.namiMembersWrapperSuccessTestData
                                    ))
                                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    )
            );

            return this;
        }

        public NamiClientWiremockSuccessConfigurator logout() {
            stubFor(get(urlEqualTo(NamiApiEndpoints.LOGOUT))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            noContent()
                                    .withHeader(
                                            HttpHeaders.SET_COOKIE,
                                            cookieWithExpiration
                                    )
                    )

            );

            return this;
        }
    }

    public static class NamiClientWiremockFailureConfigurator {
        @SneakyThrows
        public NamiClientWiremockFailureConfigurator login(String username, String password) {
            NamiLoginResponse loginResponse = new NamiLoginResponse();
            loginResponse.setStatusCode(3000);
            loginResponse.setStatusMessage("Benutzer nicht gefunden oder Passwort falsch.");

            stubFor(post(urlEqualTo(NamiApiEndpoints.LOGIN))
                    .willReturn(
                            aResponse()
                                    .withStatus(200)
                                    .withHeader(
                                            HttpHeaders.SET_COOKIE,
                                            cookie
                                    )
                                    .withBody(jsonObjectWriter.writeValueAsString(loginResponse))
                    )
            );
            return this;
        }

        public NamiClientWiremockFailureConfigurator loginNoResponseBody(String username, String password) {
            stubFor(post(urlEqualTo(NamiApiEndpoints.LOGIN))
                    .willReturn(
                            aResponse()
                                    .withStatus(200)
                                    .withHeader(
                                            HttpHeaders.SET_COOKIE,
                                            cookie
                                    )
                    )
            );
            return this;
        }

        @SneakyThrows
        public NamiClientWiremockFailureConfigurator activateLogin(HttpStatus status) {
            stubFor(get(urlEqualTo(activateLoginLocation))
                    .willReturn(
                            aResponse()
                                    .withStatus(status.value())
                                    .withHeader(
                                            HttpHeaders.SET_COOKIE,
                                            cookie
                                    )
                    )
            );
            return this;
        }

        public NamiClientWiremockFailureConfigurator activateLoginNoCookie() {
            stubFor(get(urlEqualTo(activateLoginLocation))
                    .willReturn(
                            aResponse()
                                    .withStatus(200)
                    )
            );
            return this;
        }

        public NamiClientWiremockFailureConfigurator noBodyGetAllNamiMembers(String groupingId) {
            stubFor(get(urlEqualTo(NamiApiEndpoints.allMembersOfGrouping(groupingId)))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            ok()
                                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    )
            );

            return this;
        }

        public NamiClientWiremockFailureConfigurator emptyBodyGetAllNamiMembers(String groupingId) {
            stubFor(get(urlEqualTo(NamiApiEndpoints.allMembersOfGrouping(groupingId)))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            ok()
                                    .withBody("")
                                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    )
            );

            return this;
        }

        @SneakyThrows
        public NamiClientWiremockFailureConfigurator sessionExpiredGetAllNamiMembers(String groupId) {
            NamiMembersWrapper namiMembersWrapper = new NamiMembersWrapper();
            namiMembersWrapper.setSuccess(false);
            namiMembersWrapper.setResponseType("ERROR");
            namiMembersWrapper.setMessage("Session expired");

            stubFor(get(urlEqualTo(NamiApiEndpoints.allMembersOfGrouping(groupId)))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            ok()
                                    .withBody(jsonObjectWriter.writeValueAsString(namiMembersWrapper))
                    )
            );

            return this;
        }

        @SneakyThrows
        public NamiClientWiremockFailureConfigurator accessViolationGetAllNamiMembers(String groupId) {
            NamiMembersWrapper namiMembersWrapper = new NamiMembersWrapper();
            namiMembersWrapper.setSuccess(false);
            namiMembersWrapper.setResponseType("EXCEPTION");
            namiMembersWrapper.setMessage("Sicherheitsverletzung: Zugriff auf Rechte Recht (n:2001002 o:2) fehlgeschlagen");

            stubFor(get(urlEqualTo(NamiApiEndpoints.allMembersOfGrouping(groupId)))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            ok()
                                    .withBody(jsonObjectWriter.writeValueAsString(namiMembersWrapper))
                    )
            );

            return this;
        }

        @SneakyThrows
        public NamiClientWiremockFailureConfigurator noResponseTypeGetAllNamiMembers(String groupId) {
            NamiMembersWrapper namiMembersWrapper = new NamiMembersWrapper();
            namiMembersWrapper.setSuccess(false);
            namiMembersWrapper.setResponseType("UNKNOWN");
            namiMembersWrapper.setMessage("Unknown responseType");

            stubFor(get(urlEqualTo(NamiApiEndpoints.allMembersOfGrouping(groupId)))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            ok()
                                    .withBody(jsonObjectWriter.writeValueAsString(namiMembersWrapper))
                    )
            );

            return this;
        }

        public NamiClientWiremockFailureConfigurator noCookieExpirationlogout() {
            stubFor(get(urlEqualTo(NamiApiEndpoints.LOGOUT))
                    .withCookie("JSESSIONID", matching(jsessionId))
                    .willReturn(
                            noContent()
                    )

            );

            return this;
        }
    }
}