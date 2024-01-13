package de.stinner.anmeldetool.application.rest.controllers;

import de.stinner.anmeldetool.application.rest.RestApiEndpoints;
import de.stinner.anmeldetool.application.rest.models.ConfigurationDto;
import de.stinner.anmeldetool.application.rest.models.OAuth2ConfigurationDto;
import de.stinner.anmeldetool.application.rest.security.OAuth2Configuration;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientConfigurationController {

    private final OAuth2Configuration oAuth2Configuration;

    @ApiResponse(
            responseCode = "200",
            description = "Configuration for the frontend",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ConfigurationDto.class)
                    )
            }
    )
    @GetMapping(RestApiEndpoints.V1.CONFIGURATION)
    public ResponseEntity<ConfigurationDto> getConfiguration() {
        OAuth2ConfigurationDto oAuth2ConfigurationDto = OAuth2ConfigurationDto.of(oAuth2Configuration);
        ConfigurationDto dto = new ConfigurationDto(oAuth2ConfigurationDto);

        return ResponseEntity.ok(dto);
    }
}
