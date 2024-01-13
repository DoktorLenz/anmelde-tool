package de.stinner.anmeldetool.domain.error;


import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public final class ErrorResponse {

    private final String errorMessage;
    private final String path;
    private final int status;
    private final Instant timestamp = Instant.now();
    private final List<String> details;


    ErrorResponse(
            String errorMessage,
            String path,
            int status,
            List<String> details
    ) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.status = status;
        this.details = details;
    }

}
