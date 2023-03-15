package de.stinner.anmeldetoolbackend.application.rest.error;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler4xx {


    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<ErrorResponse> handleResponseStatusException(
            ResponseStatusException e,
            HttpServletRequest request
    ) {
        log.info("Resolving ResponseStatusException.");

        HttpStatus responseStatus = e.getStatus();

        String errorMessage = "";

        String exceptionReason = e.getReason();

        if (responseStatus.equals(HttpStatus.NOT_FOUND) && ObjectUtils.isEmpty(exceptionReason)) {
            errorMessage = ErrorMessages.C404.COULD_NOT_FIND_RESULT;
        } else if (!ObjectUtils.isEmpty(exceptionReason)) {
            errorMessage = exceptionReason;
        } else if (responseStatus.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            errorMessage = ErrorMessages.C500.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                errorMessage,
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    //<editor-fold desc="Http 400">

    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageConversionException(
            HttpMessageConversionException e,
            HttpServletRequest request
    ) {
        log.info("Resolving HttpMessageConversionException.");

        String detailsMessage = "";
        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            detailsMessage = ExceptionHandlerHelper.createDetailsMessageForInvalidFormatException(
                    invalidFormatException
            );
        } else if (cause instanceof JsonParseException jsonParseException) {
            detailsMessage = (jsonParseException).getOriginalMessage();
        }

        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C400.MALFORMED_REQUEST_BODY,
                request.getRequestURI(),
                responseStatus.value(),
                detailsMessage
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            HttpServletRequest request,
            MethodArgumentNotValidException e
    ) {
        log.info("Resolving MethodArgumentNotValidException.");

        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        List<String> errors = ExceptionHandlerHelper.getErrorsFromValidation(e);

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C400.MALFORMED_REQUEST_BODY,
                request.getRequestURI(),
                responseStatus.value(),
                errors
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            HttpServletRequest request,
            MethodArgumentTypeMismatchException e
    ) {
        log.info("Resolving MethodArgumentTypeMismatchException.");

        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        String detailMessage = "";

        Class<?> requiredType = e.getRequiredType();

        if (requiredType != null) {
            String requiredTypeName = requiredType.getSimpleName();
            String receivedValue = Objects.toString(e.getValue());

            detailMessage = "Cannot cast value: '" + receivedValue + "' to type: '" + requiredTypeName + "'";

        }

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C400.MALFORMED_REQUEST_URL,
                request.getRequestURI(),
                responseStatus.value(),
                detailMessage
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(
            HttpServletRequest request,
            BindException e
    ) {
        log.info("Resolving BindException.");

        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C400.BAD_REQUEST,
                request.getRequestURI(),
                responseStatus.value(),
                ExceptionHandlerHelper.createDetailsMessageForBindingException(e)
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<ErrorResponse> handlePropertyReferenceException(
            HttpServletRequest request,
            PropertyReferenceException e
    ) {
        log.info("Resolving PropertyReferenceException.");

        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C400.BAD_REQUEST,
                request.getRequestURI(),
                responseStatus.value(),
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    //</editor-fold>

    //<editor-fold desc="Http 403">

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException e,
            HttpServletRequest request
    ) {
        log.info("Resolving AccessDeniedException.");

        HttpStatus responseStatus = HttpStatus.FORBIDDEN;

        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    //</editor-fold>

    //<editor-fold desc="Http 404">

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(HttpServletRequest request) {
        log.info("Resolving EmptyResultDataAccessException.");

        HttpStatus responseStatus = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C404.COULD_NOT_FIND_RESULT,
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(HttpServletRequest request) {
        log.info("Resolving EntityNotFoundException.");

        HttpStatus responseStatus = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C404.COULD_NOT_FIND_RESULT,
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    //</editor-fold>

    //<editor-fold desc="Http 405">

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request,
            HttpRequestMethodNotSupportedException e
    ) {
        log.info("Resolving HttpRequestMethodNotSupportedException.");

        HttpStatus responseStatus = HttpStatus.METHOD_NOT_ALLOWED;

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorMessages.C405.MEHTOD_NOT_ALLOWED,
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    //</editor-fold>

    //<editor-fold desc="Http 406">

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(
            HttpMediaTypeNotAcceptableException e,
            HttpServletRequest request
    ) {
        log.info("Resolving HttpMediaTypeNotAcceptableException.");

        HttpStatus responseStatus = HttpStatus.NOT_ACCEPTABLE;

        String errorMessage = "Media type '" + request.getHeader("Accept")
                + "' not supported. This API can only generate: '" + e.getSupportedMediaTypes() + "'.";

        //Workaround to force "application/json" in response. If not present, Spring would view the response of this
        //handler as an error, as the wrong content type were returned.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(
                errorMessage,
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, headers, responseStatus);
    }

    //</editor-fold>

    //<editor-fold desc="Http 415">

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e,
            HttpServletRequest request
    ) {
        log.info("Resolving HttpMediaTypeNotSupportedException.");

        HttpStatus responseStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        String errorMessage = "Media type '" + e.getContentType()
                + "' not supported. This API only supports: '" + MediaType.APPLICATION_JSON_VALUE + "'.";

        ErrorResponse errorResponse = new ErrorResponse(
                errorMessage,
                request.getRequestURI(),
                responseStatus.value()
        );

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    //</editor-fold>

}
