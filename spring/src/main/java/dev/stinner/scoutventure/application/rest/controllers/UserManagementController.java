package dev.stinner.scoutventure.application.rest.controllers;

import dev.stinner.scoutventure.application.rest.RestApiEndpoints;
import dev.stinner.scoutventure.application.rest.models.NamiImportDetailsDto;
import dev.stinner.scoutventure.application.rest.models.NamiMemberDto;
import dev.stinner.scoutventure.application.rest.models.NamiMemberUpdateDto;
import dev.stinner.scoutventure.application.rest.models.UserDto;
import dev.stinner.scoutventure.application.rest.security.Role;
import dev.stinner.scoutventure.domain.error.ErrorMessages;
import dev.stinner.scoutventure.domain.error.ErrorResponse;
import dev.stinner.scoutventure.domain.ports.api.NamiMemberService;
import dev.stinner.scoutventure.domain.ports.api.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class UserManagementController {

    private final NamiMemberService namiMemberService;
    private final UserService userService;


    @RolesAllowed({Role.ADMIN})
    @GetMapping(RestApiEndpoints.V1.Usermanagement.USERS)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers()
                .stream()
                .map(UserDto::fromDomain)
                .toList();

        return ResponseEntity.ok(users);
    }

    @RolesAllowed({Role.ADMIN})
    @GetMapping(RestApiEndpoints.V1.Usermanagement.NAMI_MEMBERS)
    public ResponseEntity<List<NamiMemberDto>> getNamiMembers() {
        List<NamiMemberDto> namiMembers = namiMemberService.getNamiMembers()
                .stream()
                .map(NamiMemberDto::fromDomain)
                .toList();

        return ResponseEntity.ok(namiMembers);
    }

    @RolesAllowed({Role.ADMIN})
    @PutMapping(RestApiEndpoints.V1.Usermanagement.NAMI_MEMBER)
    public ResponseEntity<Void> updateNamiMember(@PathVariable Long memberId, @Valid @RequestBody NamiMemberUpdateDto namiMemberUpdateDto) {
        if (!memberId.equals(namiMemberUpdateDto.getMemberId())) {
            throw new ConstraintViolationException("MemberId in path does not match memberId in body.", null);
        }
        var namiMember = namiMemberService.getNamiMemberById(memberId);
        var users = userService.getAllUsers();
        namiMember = namiMemberUpdateDto.applyToDomainModel(namiMember, users);
        namiMemberService.updateNamiMember(namiMember);
        return ResponseEntity.noContent().build();
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
