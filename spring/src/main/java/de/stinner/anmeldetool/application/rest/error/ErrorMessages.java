package de.stinner.anmeldetool.application.rest.error;


import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class ErrorMessages {

    /* 4xx */
    public static final String DEFAULT_4XX_MESSAGE = "Bad request.";
    public static final String MALFORMED_REQUEST_BODY = "Request body was malformed.";
    public static final String MALFORMED_REQUEST_URL = "Request URL was malformed.";
    public static final String NOT_FOUND = "Could not find the requested resource.";
    public static final String METHOD_NOT_ALLOWED = "The used HTTP method is not allowed at this resource.";
    public static final String FORBIDDEN = "Access denied.";
    public static final String NOT_MULTIPART = "Request was not a multipart request.";
    public static final String INVALID_PARAMETERS = "Parameters are invalid.";

    public static final String RELATION_NOT_FULFILLED = "Relation was not fulfilled.";

    /* 5xx */
    public static final String INTERNAL_SERVER_ERROR = "There was an internal error.";


    public static String getMediaTypeErrorMessage(
            String providedMediaType,
            List<MediaType> supported
    ) {
        return "Media type '" + providedMediaType
                + "' not supported. This API can only generate: '" + supported + "'.";
    }

    public static String getContentTypeErrorMessage(
            MediaType providedContentType,
            MediaType... supported
    ) {
        return "Media type '" + providedContentType
                + "' not supported. This API only supports: '" + Arrays.toString(supported) + "'.";
    }

}
