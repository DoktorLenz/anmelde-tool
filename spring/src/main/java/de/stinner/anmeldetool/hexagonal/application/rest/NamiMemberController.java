package de.stinner.anmeldetool.hexagonal.application.rest;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.hexagonal.application.rest.models.NamiMemberDto;
import de.stinner.anmeldetool.hexagonal.domain.ports.api.NamiMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NamiMemberController {

    private final NamiMemberService namiMemberService;

    @GetMapping(ApiEndpoints.V1.Usermanagement.NAMI_MEMBERS)
    public ResponseEntity<List<NamiMemberDto>> getNamiMembers() {
        List<NamiMemberDto> namiMembers = namiMemberService.getNamiMembers()
                .stream()
                .map(NamiMemberDto::fromDomain)
                .toList();

        return ResponseEntity.ok(namiMembers);
    }
}
