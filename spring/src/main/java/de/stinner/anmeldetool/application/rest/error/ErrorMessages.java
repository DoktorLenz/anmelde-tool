package de.stinner.anmeldetool.application.rest.error;


import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;

import java.util.List;

@UtilityClass
public final class ErrorMessages {

    /* 4xx */
    public static final String DEFAULT_4XX_MESSAGE = "Bad request.";
    public static final String MALFORMED_REQUEST_BODY = "Request body was malformed.";
    public static final String NOT_FOUND = "Could not find the requested resource.";
    public static final String METHOD_NOT_ALLOWED = "The used HTTP method is not allowed at this resource.";
    public static final String FORBIDDEN = "Access denied.";
    public static final String NOT_MULTIPART = "Request was not a multipart request.";
    public static final String INVALID_PARAMETERS = "Parameters are invalid.";
    public static final String MISSING_REQUEST_BODY = "Request body is missing.";
    public static final String NAMI_LOGIN_FAILED = "Could not login with provided Nami credentials.";
    public static final String NAMI_ACCESS_VIOLATION = "Nami user does not have sufficient permissions.";
    public static final String RELATION_NOT_FULFILLED = "Relation was not fulfilled.";

    /* 5xx */
    public static final String INTERNAL_SERVER_ERROR = "There was an internal error.";


    public static String getMediaTypeNotAcceptableMessage(
            String providedType,
            List<MediaType> supported
    ) {
        return "Media type '" + providedType
                + "' not supported. This API can only generate: '" + supported + "'.";
    }

    public static String getMediaTypeNotSupportedMessage(
            String providedType,
            List<MediaType> supported
    ) {
        return "Media type '" + providedType
                + "' not supported. This API only accepts: '" + supported + "'.";
    }

}
