package dev.stinner.scoutventure.infrastructure.nami.client;

import dev.stinner.scoutventure.base.BaseIntegrationTest;
import dev.stinner.scoutventure.domain.exceptions.nami.NamiException;
import dev.stinner.scoutventure.domain.exceptions.nami.NamiUnavailableException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpMethod;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@AutoConfigureWireMock(port = 43500)
@Slf4j
class NamiRestTemplateIT extends BaseIntegrationTest {

    @Value("${scoutventure.nami.uri}")
    private String wiremockUri;

    @SneakyThrows
    @Test
    void should_use_namiResponseErrorHandler_on4xx() {
        NamiRestTemplate restTemplate = new NamiRestTemplate(HttpClients.createDefault());

        stubFor(get(urlEqualTo("/"))
                .willReturn(
                        aResponse().withStatus(400)
                )
        );

        assertThatThrownBy(() -> {
            restTemplate.exchange(wiremockUri, HttpMethod.GET, null, Void.class);
        }).isInstanceOf(NamiException.class);
    }

    @SneakyThrows
    @Test
    void should_use_namiResponseErrorHandler_on5xx() {
        NamiRestTemplate restTemplate = new NamiRestTemplate(HttpClients.createDefault());

        stubFor(get(urlEqualTo("/"))
                .willReturn(
                        aResponse().withStatus(500)
                )
        );

        assertThatThrownBy(() -> {
            restTemplate.exchange(wiremockUri, HttpMethod.GET, null, Void.class);
        }).isInstanceOf(NamiException.class);
    }

    @SneakyThrows
    @Test
    void should_throw_NamiUnavailableException_onResourceAccessException() {
        NamiRestTemplate restTemplate = new NamiRestTemplate(HttpClients.createDefault());

        assertThatThrownBy(() -> {
            restTemplate.exchange("", HttpMethod.GET, null, Void.class);
        }).isInstanceOf(NamiUnavailableException.class);
    }
}
