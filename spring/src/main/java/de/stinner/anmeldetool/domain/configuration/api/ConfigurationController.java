package de.stinner.anmeldetool.domain.configuration.api;

import de.stinner.anmeldetool.domain.configuration.api.models.ConfigurationDto;
import de.stinner.anmeldetool.domain.configuration.api.models.OAuth2ConfigurationDto;
import de.stinner.anmeldetool.hexagonal.application.rest.RestApiEndpoints;
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
public class ConfigurationController {

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
        OAuth2ConfigurationDto oauth2Dto = OAuth2ConfigurationDto.of(oAuth2Configuration);
        ConfigurationDto dto = new ConfigurationDto(oauth2Dto);

        return ResponseEntity.ok(dto);
    }

}
