package de.stinner.anmeldetool.domain.auth.api.model;

import de.stinner.anmeldetool.application.constraints.Password;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @Password
    private String oldPassword;

    @Password
    private String newPassword;
}
