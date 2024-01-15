package de.stinner.anmeldetool.application.rest;


import de.stinner.anmeldetool.domain.error.ErrorMessages;
import de.stinner.anmeldetool.domain.error.ErrorResponse;
import de.stinner.anmeldetool.domain.error.ErrorResponseBuilder;
import de.stinner.anmeldetool.domain.error.ExceptionHandlerHelper;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiAccessViolationException;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiLoginFailedException;
import de.stinner.anmeldetool.domain.exceptions.nami.NamiUnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<ErrorResponse> handleResponseStatusException(
            final ResponseStatusException e,
            final HttpServletRequest request
    ) {

        HttpStatus responseStatus = HttpStatus.resolve(e.getStatusCode().value());
        String exceptionReason = e.getReason();
        String errorMessage;

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(responseStatus) || null == responseStatus) {
            errorMessage = ErrorMessages.INTERNAL_SERVER_ERROR;
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("Internal Server error occurred!", e);
        } else if (HttpStatus.NOT_FOUND.equals(responseStatus)) {
            if (ObjectUtils.isEmpty(exceptionReason)) {
                errorMessage = ErrorMessages.NOT_FOUND;
            } else {
                errorMessage = exceptionReason;
            }
        } else {
            errorMessage = ErrorMessages.DEFAULT_4XX_MESSAGE;
        }

        ErrorResponse errorResponse = new ErrorResponseBuilder(errorMessage, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            final HttpServletRequest request
    ) {

        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponseBuilder(
                ErrorMessages.INVALID_PARAMETERS,
                responseStatus,
                request
        ).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(
            final HttpServletRequest request
    ) {
        return notFoundResponse(request);
    }

    private ResponseEntity<ErrorResponse> notFoundResponse(
            final HttpServletRequest request
    ) {
        HttpStatus responseStatus = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse =
                new ErrorResponseBuilder(ErrorMessages.NOT_FOUND, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException(
            final HttpServletRequest request
    ) {
        return notFoundResponse(request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(
            final HttpServletRequest request
    ) {
        HttpStatus responseStatus = HttpStatus.FORBIDDEN;

        ErrorResponse errorResponse =
                new ErrorResponseBuilder(ErrorMessages.FORBIDDEN, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final HttpServletRequest request,
            final MethodArgumentNotValidException e
    ) {
        final HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        final List<String> details = ExceptionHandlerHelper.getErrorsFromValidation(e);

        final ErrorResponse errorResponse = new ErrorResponseBuilder(
                ErrorMessages.MALFORMED_REQUEST_BODY,
                responseStatus,
                request
        ).withDetails(details).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            final HttpServletRequest request
    ) {
        final HttpStatus responseStatus = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponseBuilder(
                ErrorMessages.MISSING_REQUEST_BODY,
                responseStatus,
                request
        ).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpServletRequest request
    ) {

        HttpStatus responseStatus = HttpStatus.METHOD_NOT_ALLOWED;

        ErrorResponse errorResponse =
                new ErrorResponseBuilder(ErrorMessages.METHOD_NOT_ALLOWED, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(
            final HttpMediaTypeNotAcceptableException e,
            final HttpServletRequest request
    ) {
        HttpStatus responseStatus = HttpStatus.NOT_ACCEPTABLE;

        String errorMessage = ErrorMessages.getMediaTypeNotAcceptableMessage(
                request.getHeader(HttpHeaders.ACCEPT),
                e.getSupportedMediaTypes()
        );

        //Workaround to force "application/json" in response. If not present, Spring would view the response of this
        //handler as an error, as the wrong content type were returned.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponseBuilder(errorMessage, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, headers, responseStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
            final HttpMediaTypeNotSupportedException e,
            final HttpServletRequest request
    ) {
        HttpStatus responseStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

        String errorMessage = ErrorMessages.getMediaTypeNotSupportedMessage(
                Objects.requireNonNull(e.getContentType()).toString(),
                e.getSupportedMediaTypes()
        );

        //Workaround to force "application/json" in response. If not present, Spring would view the response of this
        //handler as an error, as the wrong content type were returned.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponseBuilder(errorMessage, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, headers, responseStatus);
    }

    @ExceptionHandler(NamiLoginFailedException.class)
    protected ResponseEntity<ErrorResponse> handleNamiLoginFailedException(
            final NamiLoginFailedException e,
            final HttpServletRequest request
    ) {
        HttpStatus responseStatus = HttpStatus.UNAUTHORIZED;

        ErrorResponse errorResponse =
                new ErrorResponseBuilder(ErrorMessages.NAMI_LOGIN_FAILED, responseStatus, request)
                        .withDetails(List.of(e.getMessage()))
                        .build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(NamiAccessViolationException.class)
    protected ResponseEntity<ErrorResponse> handleNamiAccessViolationException(final HttpServletRequest request) {
        HttpStatus responseStatus = HttpStatus.FORBIDDEN;

        ErrorResponse errorResponse =
                new ErrorResponseBuilder(ErrorMessages.NAMI_ACCESS_VIOLATION, responseStatus, request).build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }

    @ExceptionHandler(NamiUnavailableException.class)
    protected ResponseEntity<ErrorResponse> handleNamiUnavailableException(final HttpServletRequest request) {
        HttpStatus responseStatus = HttpStatus.SERVICE_UNAVAILABLE;

        ErrorResponse errorResponse = new ErrorResponseBuilder(ErrorMessages.NAMI_UNAVAILABLE, responseStatus, request)
                .build();

        return new ResponseEntity<>(errorResponse, responseStatus);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e,
            HttpServletRequest request
    ) {

        HttpStatus responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse =
                new ErrorResponseBuilder(
                        ErrorMessages.INTERNAL_SERVER_ERROR,
                        responseStatus,
                        request
                ).build();

        log.error("No specific exception-handler provided for Exception.", e);

        return new ResponseEntity<>(errorResponse, responseStatus);
    }
}
