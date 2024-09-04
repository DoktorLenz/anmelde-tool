package dev.stinner.scoutventure.application.rest.controllers;

import dev.stinner.scoutventure.application.rest.RestApiEndpoints;
import dev.stinner.scoutventure.application.rest.models.ClientConfigurationDto;
import dev.stinner.scoutventure.application.rest.models.OAuth2FrontendConfigurationDto;
import dev.stinner.scoutventure.application.rest.security.OAuth2FrontendConfiguration;
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

    private final OAuth2FrontendConfiguration oAuth2Configuration;

    @ApiResponse(
            responseCode = "200",
            description = "Configuration for the frontend",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ClientConfigurationDto.class)
                    )
            }
    )
    @GetMapping(RestApiEndpoints.V1.CONFIGURATION)
    public ResponseEntity<ClientConfigurationDto> getConfiguration() {
        OAuth2FrontendConfigurationDto oAuth2ConfigurationDto = OAuth2FrontendConfigurationDto.of(oAuth2Configuration);
        ClientConfigurationDto dto = new ClientConfigurationDto(oAuth2ConfigurationDto);

        return ResponseEntity.ok(dto);
    }
}
