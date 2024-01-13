package de.stinner.anmeldetool.infrastructure.nami.client;

import de.stinner.anmeldetool.infrastructure.nami.client.exceptions.NamiUnavailableException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class NamiRestTemplate extends RestTemplate {

    public NamiRestTemplate(HttpClient httpClient) {
        super(new HttpComponentsClientHttpRequestFactory(httpClient));

        setErrorHandler(new NamiResponseErrorHandler());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM)
        );

        setMessageConverters(List.of(converter, new FormHttpMessageConverter()));
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        try {
            return super.exchange(url, method, requestEntity, responseType, uriVariables);
        } catch (ResourceAccessException resourceAccessException) {
            throw new NamiUnavailableException();
        }
    }
}
