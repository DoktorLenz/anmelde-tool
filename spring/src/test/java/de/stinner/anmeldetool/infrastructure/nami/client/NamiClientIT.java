package de.stinner.anmeldetool.infrastructure.nami.client;

import de.stinner.anmeldetool.base.BaseIntegrationTest;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiAccessViolationException;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiException;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiLoginFailedException;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiSessionExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;

@AutoConfigureWireMock(port = 43500)
@Slf4j
@ExtendWith(OutputCaptureExtension.class)
class NamiClientIT extends BaseIntegrationTest {

    String username = "username";
    String password = "password";
    String groupingId = "groupingId";

    @Value("${anmelde-tool.nami.uri}")
    private String namiUri;

    @Test
    void when_namiImport_with_workingCredentials_then_get_namiMembers() {
        NamiClientWiremockConfigurator.INSTANCE.allSuccessful(username, password, groupingId);

        Collection<ClientNamiMember> namiMembers;
        try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
            namiMembers = namiClient.getAllMembersOfGrouping(groupingId);

        }

        assertThat(namiMembers)
                .containsExactlyInAnyOrderElementsOf(NamiTestData.namiMembersWrapperSuccessTestData.getData());
    }

    @Test
    void when_NamiLogout_successful_then_cookieStore_shouldBe_clear(CapturedOutput output) {
        NamiClientWiremockConfigurator.INSTANCE.allSuccessful(username, password, groupingId);

        NamiClient namiClient = new NamiClient(namiUri, username, password);
        namiClient.close();

        assertThat(output.getOut()).isEmpty();

        CookieStore cookieStore = (CookieStore) ReflectionTestUtils.getField(namiClient, "cookieStore");
        assertThat(cookieStore).isNotNull();
        assertThat(cookieStore.getCookies()).isEmpty();
    }

    @Nested
    class NamiImportFailureIT extends BaseIntegrationTest {

        @Test
        void body_isNull_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password).activateLogin().logout())
                    .failure(failure -> failure.noBodyGetAllNamiMembers(groupingId));

            try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                assertThatThrownBy(() -> {
                    namiClient.getAllMembersOfGrouping(groupingId);
                }).isInstanceOf(NamiException.class).hasMessageMatching("Empty body");
            }
        }

        @Test
        void body_isEmpty_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password).activateLogin().logout())
                    .failure(failure -> failure.emptyBodyGetAllNamiMembers(groupingId));

            try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                assertThatThrownBy(() -> {
                    namiClient.getAllMembersOfGrouping(groupingId);
                }).isInstanceOf(NamiException.class).hasMessageMatching("Empty body");
            }
        }

        @Test
        void session_expired_then_throw_NamiSessionExpiredException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password).activateLogin().logout())
                    .failure((failure -> failure.sessionExpiredGetAllNamiMembers(groupingId)));

            try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                assertThatThrownBy(() -> {
                    namiClient.getAllMembersOfGrouping(groupingId);
                }).isInstanceOf(NamiSessionExpiredException.class);
            }
        }

        @Test
        void accessViolation_then_throw_NamiAccessViolationException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password).activateLogin().logout())
                    .failure((failure -> failure.accessViolationGetAllNamiMembers(groupingId)));

            try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                assertThatThrownBy(() -> {
                    namiClient.getAllMembersOfGrouping(groupingId);
                }).isInstanceOf(NamiAccessViolationException.class);
            }
        }

        @Test
        void no_responseType_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password).activateLogin().logout())
                    .failure((failure -> failure.noResponseTypeGetAllNamiMembers(groupingId)));

            try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                assertThatThrownBy(() -> {
                    namiClient.getAllMembersOfGrouping(groupingId);
                })
                        .isInstanceOf(NamiException.class)
                        .hasMessageMatching("Unhandled responseType: UNKNOWN. Message: Unknown responseType");
            }
        }
    }

    @Nested
    class NamiLoginFailureIT extends BaseIntegrationTest {

        @Test
        void loginIs5xx_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password))
                    .failure(failure -> failure.activateLogin(HttpStatus.INTERNAL_SERVER_ERROR));


            assertThatThrownBy(() -> {
                // noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiException.class).hasMessageMatching("Nami error 500");
        }

        @Test
        void loginIs4xx_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password))
                    .failure(failure -> failure.activateLogin(HttpStatus.BAD_REQUEST));


            assertThatThrownBy(() -> {
                // noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiException.class).hasMessageMatching("Nami error 400");
        }

        @Test
        void loginIs204_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password))
                    .failure(failure -> failure.activateLogin(HttpStatus.NO_CONTENT));

            assertThatThrownBy(() -> {
                // noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiException.class).hasMessageMatching("Session activation error");
        }

        @Test
        void loginIs2xxNot200_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password))
                    .failure(failure -> failure.activateLogin(HttpStatus.ACCEPTED));

            assertThatThrownBy(() -> {
                // noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiException.class).hasMessageMatching("Unexpected status code 202 ACCEPTED");
        }

        @Test
        void noSessionCookie_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password))
                    .failure(NamiClientWiremockConfigurator.NamiClientWiremockFailureConfigurator::activateLoginNoCookie);

            assertThatThrownBy(() -> {
                // noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiException.class).hasMessageMatching("No session cookie received");
        }

        @Test
        void noLoginResponseBody_then_throw_NamiException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .failure(failure -> failure.loginNoResponseBody(username, password));

            assertThatThrownBy(() -> {
                // noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiException.class).hasMessageMatching("Empty body");
        }

        @Test
        void wrongCredentials_then_throw_NamiLoginFailedException() {
            NamiClientWiremockConfigurator.INSTANCE
                    .failure(failure -> failure.login(anyString(), anyString()));

            assertThatThrownBy(() -> {
                //noinspection EmptyTryBlock
                try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
                }
            }).isInstanceOf(NamiLoginFailedException.class);
        }
    }


    @Nested
    @ExtendWith(OutputCaptureExtension.class)
    class NamiLogoutFailureIT extends BaseIntegrationTest {
        @Test
        void logoutCookieNotExpired_then_log_warning(CapturedOutput output) {
            NamiClientWiremockConfigurator.INSTANCE
                    .success(success -> success.login(username, password).activateLogin())
                    .failure(NamiClientWiremockConfigurator.NamiClientWiremockFailureConfigurator::noCookieExpirationlogout);

            NamiClient namiClient = new NamiClient(namiUri, username, password);
            namiClient.close();

            assertThat(output.getOut()).contains("Nami logout not successful. Cookie was not expired.");
        }
    }
}
