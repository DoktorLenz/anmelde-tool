package de.stinner.anmeldetool.hexagonal.domain.error;

import de.stinner.anmeldetool.domain.error.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class ErrorResponseTest {

    @Test
    void errorResponseShouldContainErrorMessagePathStatusAndDetails() {
        String errorMessage = "errorMessage";
        String path = "path/to/endpoint";
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        List<String> details = List.of("Detail1", "Detail2");
        ErrorResponse response = new ErrorResponse(errorMessage, path, status, details);

        assertThat(response.getErrorMessage()).isEqualTo(errorMessage);
        assertThat(response.getPath()).isEqualTo(path);
        assertThat(response.getStatus()).isEqualTo(status);
        assertThat(response.getDetails()).containsAll(details);
    }

    @Test
    void errorResponseShouldContainExactTimeStamp() {
        String instantExpected = "2014-12-22T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        Instant instant = Instant.now(clock);

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(instant);
            ErrorResponse response = new ErrorResponse(null, null, 0, null);

            assertThat(response.getTimestamp()).hasToString(instantExpected);
        }
    }
}
