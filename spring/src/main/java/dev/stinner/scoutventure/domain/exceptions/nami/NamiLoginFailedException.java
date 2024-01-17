package dev.stinner.scoutventure.domain.exceptions.nami;

public class NamiLoginFailedException extends RuntimeException {
    public NamiLoginFailedException(String message) {
        super(message);
    }
}
