package de.stinner.anmeldetool.domain.nami.service.exceptions;

public class NamiLoginFailedException extends RuntimeException {
    public NamiLoginFailedException(String message) {
        super(message);
    }
}
