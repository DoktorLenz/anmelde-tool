package de.stinner.anmeldetool.domain.nami.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.application.rest.error.ErrorMessages;
import de.stinner.anmeldetool.application.rest.error.ErrorResponse;
import de.stinner.anmeldetool.domain.authorization.userroles.model.Role;
import de.stinner.anmeldetool.domain.nami.api.models.NamiFetchDetailsDto;
import de.stinner.anmeldetool.domain.nami.service.NamiService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NamiController {
    private final NamiService namiService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fetch was successful"),
            @ApiResponse(
                    responseCode = "401",
                    description = ErrorMessages.NAMI_LOGIN_FAILED,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = ErrorMessages.NAMI_ACCESS_VIOLATION,
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    })
    })
    @RolesAllowed({Role.ADMIN})
    @PostMapping(ApiEndpoints.V1.Nami.MEMBER_FETCH)
    public ResponseEntity<Void> fetchAllNamiMembers(@RequestBody NamiFetchDetailsDto fetchDetailsDto) {
        namiService.namiImport(fetchDetailsDto.getUsername(), fetchDetailsDto.getPassword(), fetchDetailsDto.getGroupingId());

        return ResponseEntity.noContent().build();
    }
}
