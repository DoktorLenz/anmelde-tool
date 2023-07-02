package de.stinner.anmeldetool.domain.nami.service.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NamiAccessViolationException extends RuntimeException {
    public NamiAccessViolationException(String message) {
        super(message);
    }
}
