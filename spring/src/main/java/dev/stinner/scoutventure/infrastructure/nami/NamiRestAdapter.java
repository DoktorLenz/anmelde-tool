package dev.stinner.scoutventure.infrastructure.nami;

import dev.stinner.scoutventure.domain.models.NamiMember;
import dev.stinner.scoutventure.domain.ports.spi.NamiAdapter;
import dev.stinner.scoutventure.infrastructure.nami.client.ClientNamiMember;
import dev.stinner.scoutventure.infrastructure.nami.client.NamiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NamiRestAdapter implements NamiAdapter {

    @Value("${sv.nami.url}")
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
