package de.stinner.anmeldetool.domain.configuration.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ConfigurationDto {
    private OAuth2ConfigurationDto oauth2Configuration;
}
