package de.stinner.anmeldetool.domain.auth.service;

import de.stinner.anmeldetool.domain.mail.service.AuthenticationMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResetPasswordEmailJob {
    private final AuthenticationService authenticationService;
    private final AuthenticationMailService mailService;

    @Scheduled(cron = "${anmelde-tool.reset-password.mail.pending-cron}")
    void sendPendingResetPasswordEmails() {
        mailService.sendPendingResetPasswordEmails();
    }

    @Scheduled(cron = "${anmelde-tool.reset-password.cleanup.cron}")
    void cleanupOldResetPasswordRequests() {
        authenticationService.cleanupOldResetPasswordRequests();
    }
}
