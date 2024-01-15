package de.stinner.anmeldetool.domain.error;

import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

public class MethodArgumentNotValidExceptionTestData {

    public static final String FIELD_NAME_1 = "username";
    public static final String REJECTED_VALUE_1 = "12345";
    public static final String EXPLANATION_1 = "must be at least 6 characters long";
    public static final String FIELD_NAME_2 = "email";
    public static final String REJECTED_VALUE_2 = "example.com";
    public static final String EXPLANATION_2 = "must be a valid email address";

    @SneakyThrows
    public static MethodArgumentNotValidException getMethodArgumentNotValidException() {
        FieldError fieldError1 = new FieldError("user", FIELD_NAME_1, REJECTED_VALUE_1, false, null, null, EXPLANATION_1);
        FieldError fieldError2 = new FieldError("user", FIELD_NAME_2, REJECTED_VALUE_2, false, null, null, EXPLANATION_2);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(new MethodParameter(String.class.getMethod("toString"), -1), new MapBindingResult(Map.of(), "test"));

        exception.getBindingResult().addError(fieldError1);
        exception.getBindingResult().addError(fieldError2);

        return exception;

    }

    @SneakyThrows
    public static MethodArgumentNotValidException getMethodArgumentNotValidExceptionWithoutRejectedValue() {
        FieldError fieldError1 = new FieldError("user", FIELD_NAME_1, null, false, null, null, null);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(new MethodParameter(String.class.getMethod("toString"), -1), new MapBindingResult(Map.of(), "test"));

        exception.getBindingResult().addError(fieldError1);

        return exception;
    }
}