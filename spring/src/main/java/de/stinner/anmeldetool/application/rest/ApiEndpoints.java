package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoints {

    private static final String BASE = "/api";

    @UtilityClass
    public static class V1 {
        private static final String BASE_V1 = BASE + "/v1";
        public static final String SESSION = BASE_V1 + "/session";

        @UtilityClass
        public static class Auth {
            private static final String BASE_AUTH = BASE_V1 + "/auth";
            public static final String LOGIN = BASE_AUTH + "/login";
            public static final String LOGOUT = BASE_AUTH + "/logout";
            public static final String REGISTER = BASE_AUTH + "/registration";
            public static final String FINISH_REGISTRATION = BASE_AUTH + "/finish-registration";
            public static final String FORGOT_PASSWORD = BASE_AUTH + "/forgot-password";
            public static final String RESET_PASSWORD = BASE_AUTH + "/reset-password";
            public static final String CHANGE_PASSWORD = BASE_AUTH + "/change-password";
        }


    }
}
