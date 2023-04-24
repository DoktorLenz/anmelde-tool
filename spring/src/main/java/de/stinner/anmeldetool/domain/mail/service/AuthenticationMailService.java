package de.stinner.anmeldetool.domain.mail.service;

import de.stinner.anmeldetool.domain.auth.persistence.RegistrationEntity;
import de.stinner.anmeldetool.domain.auth.persistence.RegistrationRepository;
import de.stinner.anmeldetool.domain.auth.persistence.ResetPasswordEntity;
import de.stinner.anmeldetool.domain.auth.persistence.ResetPasswordRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@EnableAsync
@Service
@RequiredArgsConstructor
public class AuthenticationMailService {

    private final MailServiceImpl mailService;
    private final RegistrationRepository registrationRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    @Value("${anmelde-tool.baseUrl}")
    private String baseUrl;

    public void sendPendingRegistrationEmails() {
        registrationRepository.findAllByEmailSentIsNull().forEach(this::sendRegistrationEmail);
    }

    @Async
    @Transactional()
    public void sendRegistrationEmail(RegistrationEntity registration) {
        Map<String, Object> templateModel = new HashMap<>();
        String recipientName = String.format("%s %s", registration.getFirstname(), registration.getLastname());
        String registrationLink = String.format(
                "%s/auth/finish-registration?id=%s",
                baseUrl,
                registration.getRegistrationId()
        );

        String subject = "Registrierung Abschließen";

        templateModel.put("recipientName", recipientName);
        templateModel.put("registrationLink", registrationLink);
        try {
            mailService.sendMessageUsingThymeleafTemplate(
                    registration.getEmail(),
                    subject,
                    "template-registration.html",
                    templateModel
            );
            registrationEmailSent(registration);
        } catch (MessagingException e) {
            throw new SendMailException(
                    registration.getEmail(),
                    subject,
                    e
            );
        }
    }

    private void registrationEmailSent(RegistrationEntity registration) {
        registration.setEmailSent(Instant.now());
        registrationRepository.save(registration);
    }

    public void sendPendingResetPasswordEmails() {
        resetPasswordRepository.findAllByEmailSentIsNull().forEach(this::sendResetPasswordEmail);
    }

    @Async
    @Transactional()
    public void sendResetPasswordEmail(ResetPasswordEntity resetPasswordEntity) {
        Map<String, Object> templateModel = new HashMap<>();
        String recipientName = String.format(
                "%s %s",
                resetPasswordEntity.getUser().getFirstname(),
                resetPasswordEntity.getUser().getLastname()
        );
        String resetPasswordLink = String.format(
                "%s/auth/reset-password?id=%s",
                baseUrl,
                resetPasswordEntity.getResetId()
        );

        String subject = "Passwort zurücksetzen";

        templateModel.put("recipientName", recipientName);
        templateModel.put("passwordResetLink", resetPasswordLink);
        try {
            mailService.sendMessageUsingThymeleafTemplate(
                    resetPasswordEntity.getUser().getEmail(),
                    subject,
                    "template-reset-password.html",
                    templateModel
            );
            passwordResetEmailSent(resetPasswordEntity);
        } catch (MessagingException e) {
            throw new SendMailException(
                    resetPasswordEntity.getUser().getEmail(),
                    subject,
                    e
            );
        }
    }

    private void passwordResetEmailSent(ResetPasswordEntity resetPasswordEntity) {
        resetPasswordEntity.setEmailSent(Instant.now());
        resetPasswordRepository.save(resetPasswordEntity);
    }
}
