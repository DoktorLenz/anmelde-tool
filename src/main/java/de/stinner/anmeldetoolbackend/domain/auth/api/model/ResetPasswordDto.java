package de.stinner.anmeldetoolbackend.domain.auth.api.model;

import de.stinner.anmeldetoolbackend.application.constraints.Password;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ResetPasswordDto {
    @NotNull
    private UUID resetId;

    @Password
    private String password;
}
