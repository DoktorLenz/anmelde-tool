package de.stinner.anmeldetool.infrastructure.nami.client;

import de.stinner.anmeldetool.domain.exceptions.nami.NamiException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NamiResponseErrorHandlerTest {

    private NamiResponseErrorHandler handler;

    @BeforeEach
    void setupHandler() {
        handler = new NamiResponseErrorHandler();
    }

    @Nested
    class HasErrorMethod {

        @SneakyThrows
        @Test
        void return_false_on_statusCode_2xx() {
            try (ClientHttpResponse response = new MockClientHttpResponse(new byte[0], 200)) {
                assertThat(handler.hasError(response)).isFalse();
            }
        }

        @SneakyThrows
        @Test
        void return_false_on_statusCode_3xx() {
            try (ClientHttpResponse response = new MockClientHttpResponse(new byte[0], 302)) {
                assertThat(handler.hasError(response)).isFalse();
            }
        }

        @SneakyThrows
        @Test
        void return_true_on_statusCode_4xx() {
            try (ClientHttpResponse response = new MockClientHttpResponse(new byte[0], 400)) {
                assertThat(handler.hasError(response)).isTrue();
            }
        }

        @SneakyThrows
        @Test
        void return_true_on_statusCode_5xx() {
            try (ClientHttpResponse response = new MockClientHttpResponse(new byte[0], 500)) {
                assertThat(handler.hasError(response)).isTrue();
            }
        }
    }

    @Nested
    class HandleErrorMethod {

        @Test
        void throwNamiExceptionInHandleError() {
            int statusCode = 405;
            try (ClientHttpResponse response = new MockClientHttpResponse(new byte[0], statusCode)) {
                assertThatThrownBy(() -> {
                    handler.handleError(response);
                }).isInstanceOf(NamiException.class).hasMessageMatching("Nami error " + statusCode);
            }
        }
    }

}
