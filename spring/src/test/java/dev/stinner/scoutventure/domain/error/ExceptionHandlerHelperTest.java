package dev.stinner.scoutventure.domain.error;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionHandlerHelperTest {


    @Test
    void when_getErrorsFromValidation_thenErrorsReturned() {
        List<String> expected = List.of(
                "Field error on field '%s': rejected value [%s], because %s."
                        .formatted(MethodArgumentNotValidExceptionTestData.FIELD_NAME_1, MethodArgumentNotValidExceptionTestData.REJECTED_VALUE_1, MethodArgumentNotValidExceptionTestData.EXPLANATION_1),
                "Field error on field '%s': rejected value [%s], because %s."
                        .formatted(MethodArgumentNotValidExceptionTestData.FIELD_NAME_2, MethodArgumentNotValidExceptionTestData.REJECTED_VALUE_2, MethodArgumentNotValidExceptionTestData.EXPLANATION_2)
        );

        List<String> actual = ExceptionHandlerHelper.getErrorsFromValidation(MethodArgumentNotValidExceptionTestData.getMethodArgumentNotValidException());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void when_getErrorsFromValidation_withNoRejectedValue_thenErrorReturned() {
        List<String> expected = List.of(
                "Field error on field '%s': rejected value [null], because null."
                        .formatted(MethodArgumentNotValidExceptionTestData.FIELD_NAME_1)
        );

        List<String> actual = ExceptionHandlerHelper.getErrorsFromValidation(
                MethodArgumentNotValidExceptionTestData.getMethodArgumentNotValidExceptionWithoutRejectedValue()
        );

        assertThat(actual).isEqualTo(expected);
    }
}
