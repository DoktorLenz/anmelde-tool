package de.stinner.anmeldetool.hexagonal.domain.error;

import de.stinner.anmeldetool.domain.error.ErrorResponse;
import de.stinner.anmeldetool.domain.error.ErrorResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorResponseBuilderTest {

    private HttpServletRequest mockRequest;

    private ErrorResponseBuilder errorResponseBuilder;

    @BeforeEach
    public void setUp() {
        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn("/example");
        errorResponseBuilder = new ErrorResponseBuilder("Error Message", HttpStatus.BAD_REQUEST, mockRequest);
    }

    @Test
    void build_withNoDetails_returnsErrorResponseWithEmptyDetails() {
        ErrorResponse errorResponse = errorResponseBuilder.build();

        assertThat(errorResponse.getErrorMessage()).isEqualTo("Error Message");
        assertThat(errorResponse.getPath()).isEqualTo("/example");
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getDetails()).isEmpty();
    }

    @Test
    void build_withDetails_returnsErrorResponseWithProvidedDetails() {
        List<String> details = List.of("Detail 1", "Detail 2");
        ErrorResponse errorResponse = errorResponseBuilder.withDetails(details).build();

        assertThat(errorResponse.getErrorMessage()).isEqualTo("Error Message");
        assertThat(errorResponse.getPath()).isEqualTo("/example");
        assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getDetails()).containsAll(details);
    }
}
