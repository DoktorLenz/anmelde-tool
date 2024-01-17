package dev.stinner.scoutventure.base;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class IntegrationTestConfiguration {
    @Bean
    @Primary
    public RestTemplate namiApiRestTemplate() {
        HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

//        return new RestTemplateBuilder()
//                .rootUri(environment.getProperty("scoutventure.nami.uri"))
//                .defaultHeader(
//                        "Accept",
//                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
//                )
//                .build();
    }
}
