package de.stinner.anmeldetoolbackend.domain.mail.service;


import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface MailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendSimpleMessageUsingTemplate(String to,
                                        String subject,
                                        String... templateModel);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);

    void sendMessageUsingThymeleafTemplate(String to,
                                           String subject,
                                           String templatePath,
                                           Map<String, Object> templateModel)
            throws IOException, MessagingException;
}
