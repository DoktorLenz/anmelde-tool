package de.stinner.anmeldetool.domain.mail.service;

import de.stinner.anmeldetool.domain.auth.persistence.*;
import jakarta.mail.MessagingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"anmelde-tool.baseUrl=http://example.com"})
class AuthenticationMailServiceTest {

    @Mock
    private MailServiceImpl mailService;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private ResetPasswordRepository resetPasswordRepository;

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private AuthenticationMailService authenticationMailService;

    @BeforeEach()
    void setup() {
        ReflectionTestUtils.setField(authenticationMailService, "baseUrl", "https://example.com");
    }

    @Test
    @SneakyThrows
    void sendRegistrationEmailTest() {
        RegistrationEntity registration = new RegistrationEntity();
        registration.setEmail("test@example.com");
        registration.setFirstname("John");
        registration.setLastname("Doe");
        registration.setRegistrationId(UUID.randomUUID());

        authenticationMailService.sendRegistrationEmail(registration);

        Mockito.verify(mailService)
                .sendMessageUsingThymeleafTemplate(
                        "test@example.com",
                        "Registrierung Abschließen",
                        "template-registration.html",
                        getTemplateModelForRegistration(registration)
                );

        Mockito.verify(registrationRepository)
                .save(argThat((registrationEntity) -> {
                            registration.setEmailSent(registrationEntity.getEmailSent());
                            return registrationEntity.equals(registration);
                        }
                ));
    }

    private Map<String, Object> getTemplateModelForRegistration(RegistrationEntity registration) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipientName", registration.getFirstname() + " " + registration.getLastname());
        model.put("registrationLink", "https://example.com/auth/finish-registration?id=" + registration.getRegistrationId());
        return model;
    }

    @Test
    @SneakyThrows
    void sendRegistrationEmailTestWithException() {
        RegistrationEntity registration = new RegistrationEntity();
        registration.setEmail("test@example.com");
        registration.setFirstname("John");
        registration.setLastname("Doe");
        registration.setRegistrationId(UUID.randomUUID());

        MessagingException exceptionToThrow = new MessagingException("Test exception");

        Mockito.doThrow(exceptionToThrow)
                .when(mailService)
                .sendMessageUsingThymeleafTemplate(
                        anyString(), anyString(), anyString(), anyMap()
                );

        try {
            authenticationMailService.sendRegistrationEmail(registration);
        } catch (SendMailException e) {
            // Verify that the correct exception was thrown
            assertThat(e).hasCause(exceptionToThrow)
                    .hasMessageContainingAll(registration.getEmail(), "Registrierung Abschließen");
        } finally {
            // Verify that the registration entity has not been saved
            Mockito.verify(registrationRepository, Mockito.times(0))
                    .save(any(RegistrationEntity.class));
        }
    }

    @Test
    @SneakyThrows
    void sendResetPasswordEmailTest() {
        UserDataEntity userData = new UserDataEntity();
        userData.setEmail("test@example.com");
        userData.setFirstname("John");
        userData.setLastname("Doe");

        ResetPasswordEntity resetPassword = new ResetPasswordEntity();
        resetPassword.setResetId(UUID.randomUUID());
        resetPassword.setUser(userData);

        authenticationMailService.sendResetPasswordEmail(resetPassword);

        Mockito.verify(mailService)
                .sendMessageUsingThymeleafTemplate(
                        userData.getEmail(),
                        "Passwort zurücksetzen",
                        "template-reset-password.html",
                        getTemplateModelForResetPassword(resetPassword)
                );

        Mockito.verify(resetPasswordRepository)
                .save(argThat((resetPasswordEntity) -> {
                            resetPassword.setEmailSent(resetPasswordEntity.getEmailSent());
                            return resetPasswordEntity.equals(resetPassword);
                        }
                ));
    }

    private Map<String, Object> getTemplateModelForResetPassword(ResetPasswordEntity resetPassword) {
        Map<String, Object> model = new HashMap<>();
        model.put("recipientName", resetPassword.getUser().getFirstname() + " " + resetPassword.getUser().getLastname());
        model.put("passwordResetLink", "https://example.com/auth/reset-password?id=" + resetPassword.getResetId());
        return model;
    }

    @Test
    @SneakyThrows
    void sendResetPasswordEmailTestWithException() {
        UserDataEntity userData = new UserDataEntity();
        userData.setEmail("test@example.com");
        userData.setFirstname("John");
        userData.setLastname("Doe");

        ResetPasswordEntity resetPassword = new ResetPasswordEntity();
        resetPassword.setResetId(UUID.randomUUID());
        resetPassword.setUser(userData);

        MessagingException exceptionToThrow = new MessagingException("Test exception");

        Mockito.doThrow(exceptionToThrow)
                .when(mailService)
                .sendMessageUsingThymeleafTemplate(
                        anyString(), anyString(), anyString(), anyMap()
                );

        try {
            authenticationMailService.sendResetPasswordEmail(resetPassword);
        } catch (SendMailException e) {
            // Verify that the correct exception was thrown
            assertThat(e).hasCause(exceptionToThrow)
                    .hasMessageContainingAll(userData.getEmail(), "Passwort zurücksetzen");
        } finally {
            // Verify that the reset password entity has not been saved
            Mockito.verify(resetPasswordRepository, Mockito.times(0))
                    .save(any(ResetPasswordEntity.class));
        }
    }
}
