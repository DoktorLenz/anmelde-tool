package de.stinner.anmeldetool.application.rest.error;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.*;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestControllerExceptionHandlerTest {

    private final RestControllerExceptionHandler handler = new RestControllerExceptionHandler();

    private final String path = "/test";
    private final MediaType accept = MediaType.APPLICATION_JSON;

    private HttpServletRequest request;


    @BeforeEach
    void setUpRequest() {
        final ServletContext servletContext = new MockServletContext();

        request = MockMvcRequestBuilders.request(HttpMethod.GET, path)
                .accept(accept)
                .buildRequest(servletContext);

    }

    private void validate(
            final ResponseEntity<ErrorResponse> result,
            final HttpStatus expectedStatus,
            final String expectedErrorMessage
    ) {
        assertThat(result.getStatusCode()).isEqualTo(expectedStatus);
        assertThat(result.getBody()).isNotNull();
        final ErrorResponse errorResponse = result.getBody();
        assertThat(errorResponse.getStatus()).isEqualTo(expectedStatus.value());
        assertThat(errorResponse.getErrorMessage()).isEqualTo(expectedErrorMessage);
        assertThat(errorResponse.getPath()).isEqualTo(path);
    }

    @Nested
    @ExtendWith(OutputCaptureExtension.class)
    class ResponseStatusExceptionTest {
        @Test
        void responseStatusException500Returns500(CapturedOutput output) {
            final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            final ResponseStatusException e = new ResponseStatusException(status);
            final ResponseEntity<ErrorResponse> response = handler.handleResponseStatusException(e, request);

            validate(response, status, ErrorMessages.INTERNAL_SERVER_ERROR);
            // Internal Server error must be logged!!!
            assertThat(output.getOut()).contains("Internal Server error occurred!", e.toString());
        }

        @Test
        void responseStatusExceptionNullReturns500(CapturedOutput output) {
            try (MockedStatic<HttpStatus> httpStatusMock = Mockito.mockStatic(HttpStatus.class)) {
                httpStatusMock.when(() -> HttpStatus.resolve(Mockito.anyInt())).thenReturn(null);

                final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                final ResponseStatusException e = new ResponseStatusException(status);
                final ResponseEntity<ErrorResponse> response = handler.handleResponseStatusException(e, request);

                validate(response, status, ErrorMessages.INTERNAL_SERVER_ERROR);
                // Internal Server error must be logged!!!
                assertThat(output.getOut()).contains("Internal Server error occurred!", e.toString());
            }
        }

        @Test
        void responseStatusException404NoReasonReturns404() {
            final HttpStatus status = HttpStatus.NOT_FOUND;
            final ResponseStatusException e = new ResponseStatusException(status);

            final ResponseEntity<ErrorResponse> response = handler.handleResponseStatusException(e, request);

            validate(response, status, ErrorMessages.NOT_FOUND);
        }

        @Test
        void responseStatusException404WithReasonReturns404() {
            final HttpStatus status = HttpStatus.NOT_FOUND;
            final String reason = "test-reason";
            final ResponseStatusException e = new ResponseStatusException(status, reason);

            final ResponseEntity<ErrorResponse> response = handler.handleResponseStatusException(e, request);

            validate(response, status, reason);

        }

        @Test
        void responseStatusException409WithReasonReturns409() {
            final HttpStatus status = HttpStatus.FORBIDDEN;
            final String reason = "test-reason";
            final ResponseStatusException e = new ResponseStatusException(status, reason);

            final ResponseEntity<ErrorResponse> response = handler.handleResponseStatusException(e, request);
            validate(response, status, ErrorMessages.DEFAULT_4XX_MESSAGE);
        }

    }

    @Nested
    class ConstraintViolationExceptionTest {
        @Test
        void handleConstraintViolationExceptionReturns400() {
            validate(
                    handler.handleConstraintViolationException(request),
                    HttpStatus.BAD_REQUEST,
                    ErrorMessages.INVALID_PARAMETERS
            );
        }
    }

    @Nested
    class EmptyResultDataAccessExceptionTest {
        @Test
        void handleEmptyResultDataAccessExceptionReturns404() {
            validate(
                    handler.handleEmptyResultDataAccessException(request),
                    HttpStatus.NOT_FOUND,
                    ErrorMessages.NOT_FOUND
            );
        }
    }

    @Nested
    class NoSuchElementExceptionTest {
        @Test
        void handleNoSuchElementExceptionReturns404() {
            validate(
                    handler.handleNoSuchElementException(request),
                    HttpStatus.NOT_FOUND,
                    ErrorMessages.NOT_FOUND
            );
        }
    }

    @Nested
    class HttpMessageNotReadableExceptionTest {
        @Test
        void handleHttpMessageNotReadableExceptionReturns400() {
            validate(
                    handler.handleHttpMessageNotReadableException(request),
                    HttpStatus.BAD_REQUEST,
                    ErrorMessages.MISSING_REQUEST_BODY
            );
        }
    }

    @Nested
    class HttpRequestMethodNotSupportedExceptionTest {
        @Test
        void handleHttpRequestMethodNotSupportedExceptionReturns405() {
            validate(
                    handler.handleHttpRequestMethodNotSupportedException(request),
                    HttpStatus.METHOD_NOT_ALLOWED,
                    ErrorMessages.METHOD_NOT_ALLOWED
            );
        }
    }

    @Nested
    class HttpMediaTypeNotAcceptableExceptionTest {
        @Test
        void handleHttpMediaTypeNotAcceptableExceptionReturns406() {
            final List<MediaType> providedMediaTypes =
                    List.of(MediaType.APPLICATION_XML, MediaType.APPLICATION_ATOM_XML);

            HttpMediaTypeNotAcceptableException e = new HttpMediaTypeNotAcceptableException(providedMediaTypes);
            validate(
                    handler.handleHttpMediaTypeNotAcceptableException(e, request),
                    HttpStatus.NOT_ACCEPTABLE,
                    ErrorMessages.getMediaTypeNotAcceptableMessage(
                            accept.getType() + "/" + accept.getSubtype(),
                            providedMediaTypes
                    )
            );
            assertThat(handler.handleHttpMediaTypeNotAcceptableException(e, request).getHeaders()
            ).containsEntry(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE));
        }
    }

    @Nested
    class HttpMediaTypeNotSupportedExceptionTest {
        @Test
        void handleHttpMediaTypeNotSupportedExceptionReturns415() {
            final List<MediaType> providedMediaTypes =
                    List.of(MediaType.APPLICATION_XML, MediaType.APPLICATION_ATOM_XML);

            HttpMediaTypeNotSupportedException e = new HttpMediaTypeNotSupportedException(MediaType.APPLICATION_CBOR, providedMediaTypes);
            validate(
                    handler.handleHttpMediaTypeNotSupportedException(e, request),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    ErrorMessages.getMediaTypeNotSupportedMessage(
                            MediaType.APPLICATION_CBOR.toString(),
                            providedMediaTypes
                    )
            );
            assertThat(handler.handleHttpMediaTypeNotSupportedException(e, request).getHeaders()
            ).containsEntry(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE));
        }
    }

    @Nested
    class GenericExceptionTest {
        @Test
        void handleExceptionReturns500() {
            final RuntimeException e = new RuntimeException("test");
            validate(
                    handler.handleException(e, request),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorMessages.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Nested
    class MethodArgumentNotValidExceptionTest {
        @Test
        void handleMethodArgumentNotValidExceptionReturns400() {
            validate(
                    handler.handleMethodArgumentNotValidException(request, MethodArgumentNotValidExceptionTestData.getMethodArgumentNotValidException()),
                    HttpStatus.BAD_REQUEST,
                    ErrorMessages.MALFORMED_REQUEST_BODY
            );
        }
    }
}
