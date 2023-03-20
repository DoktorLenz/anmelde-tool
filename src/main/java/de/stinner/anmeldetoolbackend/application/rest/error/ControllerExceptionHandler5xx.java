package de.stinner.anmeldetoolbackend.application.rest.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler5xx {

    //    @ApiResponse(
//            description = "When the request is malformed in a way the backend can not (yet) handle, "
//                    + "or the backend is not running properly.",
//            responseCode = "500",
//            content = @Content(
//                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                    schema = @Schema(implementation = ErrorResponse.class)
//            )
//    )
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e,
            HttpServletRequest request
    ) {

        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C500.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                responseStatus.value()
        );

        log.error("No specific exception-handler provided for Exception.", e);

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

}
