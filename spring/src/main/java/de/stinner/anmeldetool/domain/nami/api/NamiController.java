package de.stinner.anmeldetool.domain.nami.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.authorization.userroles.model.Role;
import de.stinner.anmeldetool.domain.nami.api.models.NamiFetchDetailsDto;
import de.stinner.anmeldetool.domain.nami.service.NamiService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NamiController {
    private final NamiService namiService;

    @RolesAllowed({Role.ADMIN})
    @PostMapping(ApiEndpoints.V1.Nami.MEMBER_FETCH)
    public ResponseEntity<Void> fetchAllNamiMembers(@RequestBody NamiFetchDetailsDto fetchDetailsDto) {
        namiService.namiImport(fetchDetailsDto.getUsername(), fetchDetailsDto.getPassword(), fetchDetailsDto.getGroupId());

        return ResponseEntity.noContent().build();
    }
}
