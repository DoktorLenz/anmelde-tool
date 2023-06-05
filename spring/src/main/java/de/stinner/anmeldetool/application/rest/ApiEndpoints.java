package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoints {

    private static final String BASE = "/api";

    @UtilityClass
    public static class V1 {
        private static final String BASE_V1 = BASE + "/v1";
    }
}
