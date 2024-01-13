package de.stinner.anmeldetool.hexagonal.domain.error;

import de.stinner.anmeldetool.domain.error.ExceptionHandlerHelper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.stinner.anmeldetool.hexagonal.domain.error.MethodArgumentNotValidExceptionTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerHelperTest {


    @Test
    void when_getErrorsFromValidation_thenErrorsReturned() {
        List<String> expected = List.of(
                "Field error on field '%s': rejected value [%s], because %s."
                        .formatted(FIELD_NAME_1, REJECTED_VALUE_1, EXPLANATION_1),
                "Field error on field '%s': rejected value [%s], because %s."
                        .formatted(FIELD_NAME_2, REJECTED_VALUE_2, EXPLANATION_2)
        );

        List<String> actual = ExceptionHandlerHelper.getErrorsFromValidation(getMethodArgumentNotValidException());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void when_getErrorsFromValidation_withNoRejectedValue_thenErrorReturned() {
        List<String> expected = List.of(
                "Field error on field '%s': rejected value [null], because null."
                        .formatted(FIELD_NAME_1)
        );

        List<String> actual = ExceptionHandlerHelper.getErrorsFromValidation(
                getMethodArgumentNotValidExceptionWithoutRejectedValue()
        );

        assertThat(actual).isEqualTo(expected);
    }
}
