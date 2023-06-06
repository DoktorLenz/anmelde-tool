package de.stinner.anmeldetool.domain.configuration.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.configuration.api.models.ConfigurationDto;
import de.stinner.anmeldetool.domain.configuration.api.models.OAuth2ConfigurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConfigurationController {

    private final OAuth2Configuration oAuth2Configuration;

    @GetMapping(ApiEndpoints.V1.CONFIGURATION)
    public ResponseEntity<ConfigurationDto> getConfiguration() {
        OAuth2ConfigurationDto oauth2Dto = OAuth2ConfigurationDto.of(oAuth2Configuration);
        ConfigurationDto dto = new ConfigurationDto(oauth2Dto);

        return ResponseEntity.ok(dto);
    }

}
