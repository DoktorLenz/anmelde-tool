package de.stinner.anmeldetoolbackend.domain.auth.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ConfirmRegisterDto {
    @NotEmpty
    private UUID id;
    @Size(min = 8, max = 56)
    private String password;

}
