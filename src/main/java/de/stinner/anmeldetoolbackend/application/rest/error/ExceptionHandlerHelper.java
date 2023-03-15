package de.stinner.anmeldetoolbackend.application.rest.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ExceptionHandlerHelper {

    private static final String NULL_LITERAL = "null";


    static List<String> getErrorsFromValidation(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
                .map(ExceptionHandlerHelper::buildErrorMessageForValidationError)
                .toList();
    }

    static String createDetailsMessageForInvalidFormatException(InvalidFormatException e) {
        String explanation = "it is not a valid " + e.getTargetType() + " type";

        return createFieldErrorMessageWithExplanation(
                e.getPath().get(0).getFieldName(),
                e.getValue().toString(),
                explanation
        );

    }

    static List<String> createDetailsMessageForBindingException(BindException e) {
        List<String> detailsMessage = new ArrayList<>();
        for (ObjectError error : e.getAllErrors()) {
            detailsMessage.add(buildErrorMessageForValidationError(error));
        }
        return detailsMessage;
    }

    private static String buildErrorMessageForValidationError(ObjectError objectError) {
        if (!(objectError instanceof FieldError fieldError)) {
            return "";
        }

        Object rejectedValue = fieldError.getRejectedValue();

        String rejectedValueString = rejectedValue == null
                ? NULL_LITERAL : rejectedValue.toString();

        return createFieldErrorMessage(
                fieldError.getField(),
                rejectedValueString
        );
    }

    private static String createFieldErrorMessageWithExplanation(
            String fieldName,
            String rejectedValue,
            String explanation
    ) {
        return "Field error on field '" + fieldName
                + "': rejected value [" + rejectedValue
                + "], because " + explanation + ".";
    }

    private static String createFieldErrorMessage(
            String fieldName,
            String rejectedValue
    ) {
        return "Field error on field '" + fieldName
                + "': rejected value [" + rejectedValue
                + "].";
    }

}
