package de.stinner.anmeldetool.domain.auth.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.auth.api.model.*;
import de.stinner.anmeldetool.domain.auth.persistence.RegistrationEntity;
import de.stinner.anmeldetool.domain.auth.persistence.UserDataEntity;
import de.stinner.anmeldetool.domain.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthenticationService authenticationService;

    @GetMapping(ApiEndpoints.V1.Auth.LOGIN)
    public ResponseEntity<Void> login() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(ApiEndpoints.V1.Auth.LOGOUT)
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(ApiEndpoints.V1.Auth.REGISTER)
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequestDto registerRequestDto) {
        authenticationService.register(RegistrationEntity.of(registerRequestDto));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(ApiEndpoints.V1.Auth.FINISH_REGISTRATION)
    public ResponseEntity<Void> finishRegistration(@Valid @RequestBody FinishRegistrationDto finishRegistrationDto) {
        UserDataEntity entity = authenticationService.finishRegistration(
                finishRegistrationDto.getRegistrationId(),
                finishRegistrationDto.getPassword()
        );

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(ApiEndpoints.V1.Auth.FORGOT_PASSWORD)
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {
        authenticationService.forgotPassword(forgotPasswordRequestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(ApiEndpoints.V1.Auth.RESET_PASSWORD)
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordRequestDto) {
        authenticationService.resetPassword(
                resetPasswordRequestDto.getResetId(),
                resetPasswordRequestDto.getPassword()
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(ApiEndpoints.V1.Auth.CHANGE_PASSWORD)
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        authenticationService.changePassword(changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
