package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.exceptions;

public class NamiException extends RuntimeException {
    public NamiException(String message) {
        super(message);
    }

    public NamiException(Throwable cause) {
        super(cause);
    }
}
