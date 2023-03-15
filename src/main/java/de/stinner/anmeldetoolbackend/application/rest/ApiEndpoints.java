package de.stinner.anmeldetoolbackend.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoints {

    private static final String BASE = "/api/title-service";

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

        public static final String TITLES = BASE_V1 + "/titles";
        public static final String TITLES_FILTER_CONSTRAINTS = TITLES + "/filter-constraints";

        public static final String MOVIES = BASE_V1 + "/movies";
        public static final String MOVIE_ID = MOVIES + "/{movieId}";

        public static final String TITLES_ON_DISK = BASE_V1 + "/titles-on-disk";
    }
}
