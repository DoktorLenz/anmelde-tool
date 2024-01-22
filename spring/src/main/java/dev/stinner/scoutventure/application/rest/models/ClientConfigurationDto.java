package dev.stinner.scoutventure.application.rest.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientConfigurationDto {
    @NotNull
    private OAuth2ConfigurationDto oauth2Configuration;
}
