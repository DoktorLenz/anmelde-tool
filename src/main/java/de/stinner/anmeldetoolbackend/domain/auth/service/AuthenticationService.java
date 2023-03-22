package de.stinner.anmeldetoolbackend.domain.auth.service;

import de.stinner.anmeldetoolbackend.domain.auth.persistence.RegistrationEntity;
import de.stinner.anmeldetoolbackend.domain.auth.persistence.RegistrationRepository;
import de.stinner.anmeldetoolbackend.domain.auth.persistence.UserDataEntity;
import de.stinner.anmeldetoolbackend.domain.auth.persistence.UserDataRepository;
import de.stinner.anmeldetoolbackend.domain.mail.service.MailServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService implements UserDetailsService {
    private final UserDataRepository userDataRepository;
    private final RegistrationRepository registrationRepository;
    private final MailServiceImpl mailService;
    @Value("#{new Long('${anmelde-tool.registration.cleanup.lifespan}')}")
    private Long registrationLifespanInMinutes;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserDataEntity user = findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name '" + username + "' was not found."));

        if (ObjectUtils.isEmpty(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Arrays
                        .stream(user.getAuthorities())
                        .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                        .toList()
                )
                .accountLocked(user.isAccountLocked())
                .credentialsExpired(user.isCredentialsExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    @Transactional()
    public void register(RegistrationEntity registration) {
        if (findByEmail(registration.getEmail()).isPresent()) {
            // Request should return 201, but must not add a new registration entry
            log.info("Registration for already existing user was tried: {}. Response has been artificially slowed", registration.getEmail());
            return;
        }

        registrationRepository.save(registration);
        sendRegistrationEmail(registration);
    }


    @Transactional(readOnly = true)
    protected Optional<UserDataEntity> findByEmail(final String email) {
        return userDataRepository.findByEmail(email);
    }

    public void sendPendingRegistrationEmails() {
        registrationRepository.findByEmailSentIsNull().forEach(this::sendRegistrationEmail);
    }


    @Transactional()
    protected void registrationEmailSent(RegistrationEntity registration) {
        registration.setEmailSent(Instant.now());
        registrationRepository.save(registration);
    }

    @Async
    protected void sendRegistrationEmail(RegistrationEntity registration) {
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
    public void cleanupOldRegistrations() {
        registrationRepository.deleteByCreatedAtBeforeAndEmailSentIsNotNull(
                Instant.now().minusSeconds(60 * registrationLifespanInMinutes)
        );
    }
}