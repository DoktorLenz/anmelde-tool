package de.stinner.anmeldetool.infrastructure.nami;

import de.stinner.anmeldetool.domain.models.NamiMember;
import de.stinner.anmeldetool.domain.ports.spi.NamiAdapter;
import de.stinner.anmeldetool.infrastructure.nami.client.ClientNamiMember;
import de.stinner.anmeldetool.infrastructure.nami.client.NamiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NamiRestAdapter implements NamiAdapter {

    @Value("${anmelde-tool.nami.uri}")
    private String namiUri;

    @Override
    public List<NamiMember> getAllMembersOfGrouping(String username, String password, String groupingId) {
        List<NamiMember> namiMembers;
        try (NamiClient namiClient = new NamiClient(namiUri, username, password)) {
            namiMembers = namiClient.getAllMembersOfGrouping(groupingId)
                    .stream()
                    .map(ClientNamiMember::toDomain)
                    .toList();
        }
        return namiMembers;
    }
}
