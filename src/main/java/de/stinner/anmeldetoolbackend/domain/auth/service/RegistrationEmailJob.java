package de.stinner.anmeldetoolbackend.domain.auth.service;

import de.stinner.anmeldetoolbackend.domain.mail.service.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationEmailJob {
    private final AuthenticationService authenticationService;
    private final MailServiceImpl mailService;

    @Scheduled(cron = "${anmelde-tool.registration.mail.pending-cron}")
    void sendPendingRegistrationEmails() {
        mailService.sendPendingRegistrationEmails();
    }

    @Scheduled(cron = "${anmelde-tool.registration.cleanup.cron}")
    void cleanupOldRegistrations() {
        authenticationService.cleanupOldRegistrations();
    }
}
