package de.stinner.anmeldetool.domain.nami.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.nami.api.models.NamiFetchDetailsDto;
import de.stinner.anmeldetool.domain.nami.service.NamiService;
import de.stinner.anmeldetool.domain.nami.service.models.NamiMembersWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NamiController {
    private final NamiService namiService;

    @PostMapping(ApiEndpoints.V1.Nami.MEMBER_FETCH)
    public ResponseEntity<NamiMembersWrapper> fetchAllNamiMembers(@RequestBody NamiFetchDetailsDto fetchDetailsDto) {

        HttpCookie sessionCookie = namiService.namiLogin(fetchDetailsDto.getUsername(), fetchDetailsDto.getPassword());
        NamiMembersWrapper result = namiService.getAllNamiMembers(sessionCookie, fetchDetailsDto.getGroupId());

        return ResponseEntity.ok(result);
    }
}
