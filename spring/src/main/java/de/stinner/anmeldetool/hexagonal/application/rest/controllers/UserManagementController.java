package de.stinner.anmeldetool.hexagonal.application.rest.controllers;

import de.stinner.anmeldetool.application.rest.error.ErrorMessages;
import de.stinner.anmeldetool.application.rest.error.ErrorResponse;
import de.stinner.anmeldetool.domain.authorization.userroles.model.Role;
import de.stinner.anmeldetool.hexagonal.application.rest.RestApiEndpoints;
import de.stinner.anmeldetool.hexagonal.application.rest.models.NamiImportDetailsDto;
import de.stinner.anmeldetool.hexagonal.application.rest.models.NamiMemberDto;
import de.stinner.anmeldetool.hexagonal.domain.ports.api.NamiMemberService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserManagementController {

    private final NamiMemberService namiMemberService;

    @GetMapping(RestApiEndpoints.V1.Usermanagement.NAMI_MEMBERS)
    public ResponseEntity<List<NamiMemberDto>> getNamiMembers() {
        List<NamiMemberDto> namiMembers = namiMemberService.getNamiMembers()
                .stream()
                .map(NamiMemberDto::fromDomain)
                .toList();

        return ResponseEntity.ok(namiMembers);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Import was successful"),
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
    @PostMapping(RestApiEndpoints.V1.Usermanagement.TRIGGER_IMPORT)
    public ResponseEntity<Void> triggerNamiImport(@RequestBody NamiImportDetailsDto namiImportDetailsDto) {
        namiMemberService.triggerImport(
                namiImportDetailsDto.getUsername(),
                namiImportDetailsDto.getPassword(),
                namiImportDetailsDto.getGroupingId()
        );

        return ResponseEntity.noContent().build();
    }


}
