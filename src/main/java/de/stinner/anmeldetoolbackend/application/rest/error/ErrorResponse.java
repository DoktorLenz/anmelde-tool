package de.stinner.anmeldetoolbackend.application.rest.error;

import lombok.Data;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Data
public final class ErrorResponse {

    private final String errorMessage;

    private final String path;

    private final int status;

    private final Instant timestamp;

    private final List<String> details;


    public ErrorResponse(
            String errorMessage,
            String path,
            int status
    ) {
        this(errorMessage, path, status, Collections.emptyList());
    }

    public ErrorResponse(
            String errorMessage,
            String path,
            int status,
            String details
    ) {
        this(errorMessage, path, status, List.of(details));
    }

    public ErrorResponse(
            String errorMessage,
            String path,
            int status,
            List<String> details
    ) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.status = status;
        this.details = details;
        this.timestamp = Instant.now();
    }

}
