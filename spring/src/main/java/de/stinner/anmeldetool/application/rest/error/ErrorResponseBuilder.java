package de.stinner.anmeldetool.application.rest.error;


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

    public ErrorResponseBuilder withDetails(String... details) {
        this.details = List.of(details);
        return this;
    }

    public ErrorResponse build() {
        return new ErrorResponse(errorMessage, path, status, details);
    }


}
