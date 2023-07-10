package de.stinner.anmeldetool.domain.nami.service.exceptions;

public class NamiException extends RuntimeException {
    public NamiException(String message) {
        super(message);
    }

    public NamiException(Throwable cause) {
        super(cause);
    }
}
