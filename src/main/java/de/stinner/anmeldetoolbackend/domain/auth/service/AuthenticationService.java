package de.stinner.anmeldetoolbackend.domain.auth.service;

import de.stinner.anmeldetoolbackend.application.rest.error.ErrorMessages;
import de.stinner.anmeldetoolbackend.domain.auth.persistence.*;
import de.stinner.anmeldetoolbackend.domain.mail.service.AuthenticationMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService implements UserDetailsService {
    private final UserDataRepository userDataRepository;
    private final RegistrationRepository registrationRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final AuthenticationMailService authenticationMailService;
    @Value("#{new Long('${anmelde-tool.registration.cleanup.lifespan}')}")
    private Long registrationLifespanInMinutes;

    @Value("#{new Long('${anmelde-tool.reset-password.cleanup.lifespan}')}")
    private Long resetPasswordLifespanInMinutes;

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

    @Transactional(readOnly = true)
    protected Optional<UserDataEntity> findByEmail(final String email) {
        return userDataRepository.findByEmail(email);
    }

    @Transactional()
    public void register(RegistrationEntity registration) {
        if (findByEmail(registration.getEmail()).isPresent()) {
            // Request should return 201, but must not add a new registration entry
            log.info("Registration for already existing user was tried: {}. No email has been sent.", registration.getEmail());
            return;
        }

        registrationRepository.save(registration);
        authenticationMailService.sendRegistrationEmail(registration);
    }

    @Transactional()
    public void cleanupOldRegistrations() {
        registrationRepository.deleteAllByEmailSentIsBefore(
                Instant.now().minusSeconds(60 * registrationLifespanInMinutes)
        );
    }

    @Transactional()
    public UserDataEntity finishRegistration(UUID id, String password) {
        RegistrationEntity registrationEntity = registrationRepository
                .findByRegistrationIdAndCreatedAtIsAfterAndEmailSentIsTrue(
                        id,
                        Instant.now().minusSeconds(60 * registrationLifespanInMinutes)
                )
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ErrorMessages.C404.EXPIRED_REGISTRATION_ID
                        )
                );

        if (null == registrationEntity.getEmailSent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.C404.EXPIRED_REGISTRATION_ID);
        }

        UserDataEntity entity = UserDataEntity.create(registrationEntity, password);

        registrationRepository.deleteById(id);
        return userDataRepository.save(entity);
    }

    @Transactional()
    public void forgotPassword(String email) {
        Optional<UserDataEntity> optionalUserDataEntity = userDataRepository.findByEmail(email);

        optionalUserDataEntity.ifPresentOrElse(
                (userDataEntity) -> {
                    ResetPasswordEntity entity = ResetPasswordEntity.of(userDataEntity);
                    entity = resetPasswordRepository.save(entity);
                    authenticationMailService.sendResetPasswordEmail(entity);
                },
                () -> {
                    log.info("Forgot password for none existing user was tried: {}. No email has been sent.", email);
                }
        );
    }

    @Transactional()
    public void resetPassword(UUID resetiId, String password) {
        ResetPasswordEntity resetPasswordEntity = resetPasswordRepository
                .findByResetIdAndEmailSentAfter(
                        resetiId,
                        Instant.now().minusSeconds(60 * resetPasswordLifespanInMinutes)
                )
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ErrorMessages.C404.EXPIRED_RESET_ID
                        )
                );

        if (null == resetPasswordEntity) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.C404.EXPIRED_RESET_ID);
        }

        UserDataEntity userDataEntity = resetPasswordEntity.getUser();
        userDataEntity.setPassword(password);
        userDataRepository.save(userDataEntity);
        resetPasswordRepository.deleteAllByUserEquals(userDataEntity);
    }

    @Transactional
    public void cleanupOldResetPasswordRequests() {
        resetPasswordRepository.deleteAllByCreatedAtIsBefore(
                Instant.now().minusSeconds(60 * resetPasswordLifespanInMinutes)
        );
    }
}