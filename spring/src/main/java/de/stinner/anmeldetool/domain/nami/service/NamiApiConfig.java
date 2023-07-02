package de.stinner.anmeldetool.domain.nami.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NamiApiConfig {

    @Value("${anmelde-tool.nami.uri}")
    private String namiUri;

    @Bean
    public RestTemplate namiApiRestTemplate() {
        return new RestTemplateBuilder()
                .rootUri(namiUri)
                .defaultHeader(
                        "Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
                )
                .build();
    }
}
