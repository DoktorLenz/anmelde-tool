package de.stinner.anmeldetool.domain.authorization.userroles.api;

import de.stinner.anmeldetool.hexagonal.application.rest.RestApiEndpoints;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRolesController {

    @ApiResponse(
            responseCode = "200",
            description = "Configuration for the frontend",
            content = {
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = String.class))
                    )
            }
    )
    @GetMapping(RestApiEndpoints.V1.Auth.USERROLES)
    public ResponseEntity<List<String>> getUserRoles(Authentication authentication) {
        List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(userRoles);
    }
}
