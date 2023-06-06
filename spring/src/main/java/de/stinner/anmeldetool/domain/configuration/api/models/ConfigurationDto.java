package de.stinner.anmeldetool.domain.configuration.api.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigurationDto {
    @NotNull
    private OAuth2ConfigurationDto oauth2Configuration;
}
