package de.stinner.anmeldetool.domain.nami.service.client;

import de.stinner.anmeldetool.base.BaseIntegrationTest;
import de.stinner.anmeldetool.domain.nami.service.client.models.NamiMember;
import de.stinner.anmeldetool.domain.nami.service.exceptions.NamiException;
import de.stinner.anmeldetool.domain.nami.service.exceptions.NamiLoginFailedException;
import de.stinner.anmeldetool.testdata.NamiTestData;
import de.stinner.anmeldetool.wiremock.NamiClientWiremockConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;

@AutoConfigureWireMock(port = 43500)
@Slf4j
class NamiClientIT extends BaseIntegrationTest {

    @Value("${anmelde-tool.nami.uri}")
    private String namiUri;

    @Test
    void when_namiImport_with_workingCredentials_then_get_namiMembers() {
        String username = "username";
        String password = "password";
        String groupingId = "groupingId";

        NamiClientWiremockConfigurator.INSTANCE
                .success(success -> success
                        .login(username, password)
                        .activateLogin()
                        .getAllNamiMembers(groupingId)
                        .logout()
                );

        Collection<NamiMember> namiMembers;
        try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
            namiMembers = namiClient.getAllMembersOfGrouping(groupingId);

        }

        assertThat(namiMembers)
                .containsExactlyInAnyOrderElementsOf(NamiTestData.namiMembersWrapperSuccessTestData.getData());
    }


    @Nested
    class NamiLoginFailureTests {
        String username = "username";
        String password = "password";

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

}
