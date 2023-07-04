package de.stinner.anmeldetool.domain.nami.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.when;

class NamiApiConfigTest {
    MockedConstruction<RestTemplateBuilder> mockedRestTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    private NamiApiConfig namiApiConfig;

    @BeforeEach
    @SneakyThrows
    void setup() {
        String namiUri = "https://nami.dpsg.de";
        MockitoAnnotations.openMocks(this);

        mockedRestTemplateBuilder = mockConstruction(RestTemplateBuilder.class, (mock, context) -> {
            when(mock.rootUri(namiUri)).thenReturn(mock);
            when(mock.defaultHeader(
                    "Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
            )).thenReturn(mock);
            when(mock.build()).thenReturn(restTemplate);
        });

        namiApiConfig = new NamiApiConfig();
        ReflectionTestUtils.setField(namiApiConfig, "namiUri", namiUri);
    }

    @AfterEach
    void after() {
        mockedRestTemplateBuilder.close();
    }

    @Test
    void testProperConfigurationOfRestTemplate() {
        RestTemplate actualRestTemplate = namiApiConfig.namiApiRestTemplate();

        assertThat(actualRestTemplate).isSameAs(restTemplate);
    }
}
