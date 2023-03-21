package de.stinner.anmeldetoolbackend.domain.auth.api;

import de.stinner.anmeldetoolbackend.application.rest.ApiEndpoints;
import de.stinner.anmeldetoolbackend.domain.auth.api.model.RegistrationRequestDto;
import de.stinner.anmeldetoolbackend.domain.auth.persistence.RegistrationEntity;
import de.stinner.anmeldetoolbackend.domain.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
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
    public ResponseEntity<Void> register(@RequestBody RegistrationRequestDto registerRequestDto) {
        authenticationService.register(RegistrationEntity.of(registerRequestDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
