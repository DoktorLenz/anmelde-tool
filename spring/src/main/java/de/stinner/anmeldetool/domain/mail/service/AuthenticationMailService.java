package de.stinner.anmeldetool.domain.mail.service;

import de.stinner.anmeldetool.domain.auth.persistence.RegistrationEntity;
import de.stinner.anmeldetool.domain.auth.persistence.RegistrationRepository;
import de.stinner.anmeldetool.domain.auth.persistence.ResetPasswordEntity;
import de.stinner.anmeldetool.domain.auth.persistence.ResetPasswordRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
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

    public void sendPendingRegistrationEmails() {
        registrationRepository.findAllByEmailSentIsNull().forEach(this::sendRegistrationEmail);
    }

    @Async
    public void sendRegistrationEmail(RegistrationEntity registration) {
        Map<String, Object> templateModel = new HashMap<>();
        String recipientName = String.format("%s %s", registration.getFirstname(), registration.getLastname());
        String registrationLink = String.format(
                "https://anmeldung.dpsgkolbermoor.de/auth/finish-registration?id=%s",
                registration.getRegistrationId()
        );
        templateModel.put("recipientName", recipientName);
        templateModel.put("registrationLink", registrationLink);
        try {
            mailService.sendMessageUsingThymeleafTemplate(
                    registration.getEmail(),
                    "Registrierung Abschließen",
                    "template-registration.html",
                    templateModel
            );
            registrationEmailSent(registration);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional()
    protected void registrationEmailSent(RegistrationEntity registration) {
        registration.setEmailSent(Instant.now());
        registrationRepository.save(registration);
    }

    public void sendPendingResetPasswordEmails() {
        resetPasswordRepository.findAllByEmailSentIsNull().forEach(this::sendResetPasswordEmail);
    }

    @Async
    public void sendResetPasswordEmail(ResetPasswordEntity resetPasswordEntity) {
        Map<String, Object> templateModel = new HashMap<>();
        String recipientName = String.format(
                "%s %s",
                resetPasswordEntity.getUser().getFirstname(),
                resetPasswordEntity.getUser().getLastname()
        );
        String resetPasswordLink = String.format(
                "https://anmeldung.dpsgkolbermoor.de/auth/reset-password?id=%s",
                resetPasswordEntity.getResetId()
        );

        templateModel.put("recipientName", recipientName);
        templateModel.put("passwordResetLink", resetPasswordLink);
        try {
            mailService.sendMessageUsingThymeleafTemplate(
                    resetPasswordEntity.getUser().getEmail(),
                    "Passwort zurücksetzen",
                    "template-reset-password.html",
                    templateModel
            );
            passwordResetEmailSent(resetPasswordEntity);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional()
    protected void passwordResetEmailSent(ResetPasswordEntity resetPasswordEntity) {
        resetPasswordEntity.setEmailSent(Instant.now());
        resetPasswordRepository.save(resetPasswordEntity);
    }
}