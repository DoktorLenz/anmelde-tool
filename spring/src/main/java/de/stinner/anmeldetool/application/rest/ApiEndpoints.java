package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoints {

    private static final String BASE = "/api";

    /* Swagger */
    public static final String SWAGGER_UI_RESOURCE = BASE + "/swagger-ui.html";
    public static final String SWAGGER_UI_API = BASE + "/v3/api-docs/**";
    public static final String SWAGGER_UI_BASE = BASE + "/swagger-ui/**";

    @UtilityClass
    public static final class Actuator {
        private static final String BASE = "/actuator";
        private static final String HEALTH = BASE + "/health";
        public static final String READINESS = HEALTH + "/readiness";
        public static final String LIVENESS = HEALTH + "/liveness";
    }

    @UtilityClass
    public static class V1 {
        private static final String BASE_V1 = BASE + "/v1";

        @UtilityClass
        public static class Auth {
            private static final String AUTH = BASE_V1 + "/auth";
            public static final String LOGIN = AUTH + "/login";
            public static final String LOGOUT = AUTH + "/logout";
            public static final String REGISTER = AUTH + "/register";
            public static final String FINISH_REGISTRATION = AUTH + "/finish-registration";
            public static final String FORGOT_PASSWORD = AUTH + "/forgot-password";
            public static final String RESET_PASSWORD = AUTH + "/reset-password";
            public static final String CHANGE_PASSWORD = AUTH + "/change-password";
        }


    }
}
