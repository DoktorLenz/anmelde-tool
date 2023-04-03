package de.stinner.anmeldetool.domain.mail.service;

/**
 * Exception thrown when a message could not be sent.
 */
public class SendMailException extends RuntimeException {

    /**
     * Constructs a SendMailException.
     *
     * @param recipient recipient of the mail
     * @param subject   subject of the mail
     * @param cause     cause of the exception
     */
    public SendMailException(final String recipient, final String subject, final Throwable cause) {
        super(String.format("Could not send mail with the subject '%s' to '%s'", subject, recipient), cause);
    }
}
