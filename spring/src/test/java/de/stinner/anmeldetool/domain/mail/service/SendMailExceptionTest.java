package de.stinner.anmeldetool.domain.mail.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SendMailExceptionTest {

    @Test
    void testSendMailException() {
        String recipient = "example@example.com";
        String subject = "Test subject";
        Throwable cause = new Exception("Test cause");

        SendMailException exception = new SendMailException(recipient, subject, cause);

        assertThat(exception).hasCause(cause).hasMessageContainingAll(recipient, subject);
    }

    @Test
    void testSendMailExceptionWithoutCause() {
        String recipient = "example@example.com";
        String subject = "Test subject";

        SendMailException exception = new SendMailException(recipient, subject, null);

        assertThat(exception).hasNoCause().hasMessageContainingAll(recipient, subject);
    }
}
