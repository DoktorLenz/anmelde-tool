package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoints {

    private static final String BASE = "/api";

    @UtilityClass
    public static class V1 {
        private static final String BASE_V1 = BASE + "/v1";
    }

    @UtilityClass
    public static final class Actuator {
        private static final String BASE_ACTUATOR = BASE + "/actuator";
        private static final String HEALTH = BASE_ACTUATOR + "/health";
        public static final String READINESS = HEALTH + "/readiness";
        public static final String LIVENESS = HEALTH + "/liveness";
    }
}
