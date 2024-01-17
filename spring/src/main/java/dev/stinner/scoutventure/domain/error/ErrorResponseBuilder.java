package dev.stinner.scoutventure.domain.error;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public final class ErrorResponseBuilder {

    private final String errorMessage;
    private final String path;
    private final int status;
    private List<String> details = Collections.emptyList();

    public ErrorResponseBuilder(
            String errorMessage,
            HttpStatus status,
            HttpServletRequest request
    ) {
        this.errorMessage = errorMessage;
        this.status = status.value();
        this.path = request.getRequestURI();
    }

    public ErrorResponseBuilder withDetails(List<String> details) {
        this.details = details;
        return this;
    }

    public ErrorResponse build() {
        return new ErrorResponse(errorMessage, path, status, details);
    }
}
