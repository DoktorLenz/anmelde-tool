package de.stinner.anmeldetoolbackend.domain.auth.api.model;

import de.stinner.anmeldetoolbackend.application.constraints.Password;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FinishRegistrationDto {
    @NotNull
    private UUID registrationId;
    @Password
    private String password;

}
