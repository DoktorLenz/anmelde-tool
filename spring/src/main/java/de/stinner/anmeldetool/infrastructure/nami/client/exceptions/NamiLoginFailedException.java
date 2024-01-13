package de.stinner.anmeldetool.infrastructure.nami.client.exceptions;

public class NamiLoginFailedException extends RuntimeException {
    public NamiLoginFailedException(String message) {
        super(message);
    }
}
