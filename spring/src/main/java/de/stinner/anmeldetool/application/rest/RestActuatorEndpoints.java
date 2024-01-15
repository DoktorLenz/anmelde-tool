package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class RestActuatorEndpoints {
    private static final String BASE = "/actuator";
    private static final String HEALTH = BASE + "/health";
    public static final String READINESS = HEALTH + "/readiness";
    public static final String LIVENESS = HEALTH + "/liveness";
}
