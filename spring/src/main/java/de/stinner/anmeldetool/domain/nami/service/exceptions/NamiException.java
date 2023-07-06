package de.stinner.anmeldetool.domain.nami.service.exceptions;

public class NamiException extends RuntimeException {

    public NamiException() {
        super();
    }

    public NamiException(String message) {
        super(message);
    }

    public NamiException(Throwable cause) {
        super(cause);
    }

    public NamiException(String message, Throwable cause) {
        super(message, cause);
    }
}
