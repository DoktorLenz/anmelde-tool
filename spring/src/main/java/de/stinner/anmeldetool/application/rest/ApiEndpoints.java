package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoints {

    private static final String BASE = "/api";
    public static final String API_DOCS = BASE + "/api-docs/**";
    public static final String SWAGGER = BASE + "/swagger/**";

    @UtilityClass
    public static class V1 {
        private static final String BASE_V1 = BASE + "/v1";

        public static final String CONFIGURATION = BASE_V1 + "/configuration";

        @UtilityClass
        public static class Auth {
            private static final String BASE_AUTH = BASE_V1 + "/auth";

            public static final String USERROLES = BASE_AUTH + "/user-roles";
        }

        @UtilityClass
        public static class Nami {
            private static final String BASE_NAMI = BASE_V1 + "/nami";
            public static final String MEMBER_FETCH = BASE_NAMI + "/member-fetch";
        }

        @UtilityClass
        public static class Usermanagement {
            private static final String BASE_USERMANAGEMENT = BASE_V1 + "/usermanagement";
            public static final String NAMI_MEMBERS = BASE_USERMANAGEMENT + "/nami-members";
        }
    }
}
