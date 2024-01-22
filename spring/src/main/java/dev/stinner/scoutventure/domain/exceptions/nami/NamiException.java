package dev.stinner.scoutventure.domain.exceptions.nami;

public class NamiException extends RuntimeException {
    public NamiException(String message) {
        super(message);
    }

    public NamiException(Throwable cause) {
        super(cause);
    }
}
