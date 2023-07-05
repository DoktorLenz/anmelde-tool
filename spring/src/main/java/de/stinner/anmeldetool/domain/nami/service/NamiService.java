package de.stinner.anmeldetool.domain.nami.service;

import de.stinner.anmeldetool.domain.nami.service.client.NamiClient;
import de.stinner.anmeldetool.domain.nami.service.client.models.NamiMember;
import de.stinner.anmeldetool.domain.usermanagement.namimember.service.NamiMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;


@RequiredArgsConstructor
@Service
public class NamiService {
    private final NamiMemberService namiMemberService;
    @Value("${anmelde-tool.nami.uri}")
    private String namiUri;

    public void namiImport(String username, String password, String groupingId) {
        Collection<NamiMember> namiMembers;
        try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
            namiMembers = namiClient.getAllMembersOfGrouping(groupingId);
        }
        namiMemberService.importNamiMembers(namiMembers.stream().map(NamiMember::toNamiMemberEntity).toList());
    }
}