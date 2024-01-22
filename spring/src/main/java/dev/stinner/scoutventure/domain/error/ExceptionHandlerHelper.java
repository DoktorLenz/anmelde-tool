package dev.stinner.scoutventure.domain.error;

import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@UtilityClass
public class ExceptionHandlerHelper {

    private static final String NULL_LITERAL = "null";


    public static List<String> getErrorsFromValidation(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .map(ExceptionHandlerHelper::buildErrorMessageForValidationError)
                .toList();
    }

    private static String buildErrorMessageForValidationError(ObjectError objectError) {
        FieldError fieldError = (FieldError) objectError;

        Object rejectedValue = fieldError.getRejectedValue();

        String rejectedValueString = rejectedValue != null
                ? rejectedValue.toString() : NULL_LITERAL;

        return createFieldErrorMessage(
                fieldError.getField(),
                rejectedValueString,
                fieldError.getDefaultMessage()
        );
    }

    private static String createFieldErrorMessage(
            String fieldName,
            String rejectedValue,
            String explanation
    ) {
        return "Field error on field '%s': rejected value [%s], because %s."
                .formatted(fieldName, rejectedValue, explanation);
    }
}
