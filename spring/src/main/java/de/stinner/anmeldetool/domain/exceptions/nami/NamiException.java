package de.stinner.anmeldetool.domain.exceptions.nami;

public class NamiException extends RuntimeException {
    public NamiException(String message) {
        super(message);
    }

    public NamiException(Throwable cause) {
        super(cause);
    }
}
