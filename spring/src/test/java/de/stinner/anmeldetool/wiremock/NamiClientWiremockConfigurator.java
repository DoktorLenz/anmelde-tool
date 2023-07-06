package de.stinner.anmeldetool.wiremock;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import de.stinner.anmeldetool.application.rest.NamiApiEndpoints;
import de.stinner.anmeldetool.domain.nami.service.client.models.NamiLoginResponse;
import de.stinner.anmeldetool.testdata.NamiTestData;
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

    public NamiClientWiremockConfigurator success(
            Function<NamiClientWiremockSuccessConfigurator, NamiClientWiremockSuccessConfigurator> successConfiguration
    ) {
        successConfiguration.apply(SUCCESS_INSTANCE);
        return this;
    }

    public NamiClientWiremockConfigurator failure(
            Function<NamiClientWiremockFailureConfigurator, NamiClientWiremockFailureConfigurator> failureConfiguration
    ) {
        failureConfiguration.apply(FAILURE_INSTANCE);
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
    }
}