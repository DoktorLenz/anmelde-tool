package de.stinner.anmeldetool.domain.usermanagement.namimember.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.authorization.userroles.model.Role;
import de.stinner.anmeldetool.domain.usermanagement.namimember.api.models.NamiMemberDto;
import de.stinner.anmeldetool.domain.usermanagement.namimember.persistence.NamiMemberEntity;
import de.stinner.anmeldetool.domain.usermanagement.namimember.service.NamiMemberService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NamiMembersController {

    private final NamiMemberService namiMemberService;

    @RolesAllowed({Role.ADMIN})
    @GetMapping(ApiEndpoints.V1.Usermanagement.NAMI_MEMBERS)
    public ResponseEntity<List<NamiMemberDto>> getAllNamiMembers() {
        List<NamiMemberDto> namiMembers = namiMemberService.getAllNamiMembers()
                .stream()
                .map(NamiMemberEntity::toDto)
                .toList();

        return ResponseEntity.ok(namiMembers);
    }
}
