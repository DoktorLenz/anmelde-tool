package de.stinner.anmeldetool.hexagonal.infrastructure.nami;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;
import de.stinner.anmeldetool.hexagonal.domain.ports.spi.NamiAdapter;
import de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.ClientNamiMember;
import de.stinner.anmeldetool.hexagonal.infrastructure.nami.client.NamiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
