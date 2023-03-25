package de.stinner.anmeldetoolbackend.domain.auth.api.model;

import de.stinner.anmeldetoolbackend.application.constraints.Password;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @Password
    private String oldPassword;

    @Password
    private String newPassword;
}
