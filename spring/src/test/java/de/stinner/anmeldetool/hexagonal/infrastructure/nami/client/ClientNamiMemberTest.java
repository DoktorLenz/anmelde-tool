package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClientNamiMemberTest {

    @Test
    void testToDomain() {
        ClientNamiMember namiMember = new ClientNamiMember();
        namiMember.setMemberId(12345L);
        namiMember.setFirstname("firstname");
        namiMember.setLastname("lastname");
        namiMember.setDateOfBirth(LocalDateTime.now());
        namiMember.setRank(ClientRank.JUNGPFADFINDER);
        namiMember.setGender(ClientGender.DIVERSE);

        NamiMember domain = namiMember.toDomain();

        assertThat(domain.getMemberId()).isEqualTo(namiMember.getMemberId());
        assertThat(domain.getFirstname()).isEqualTo(namiMember.getFirstname());
        assertThat(domain.getLastname()).isEqualTo(namiMember.getLastname());
        assertThat(domain.getDateOfBirth()).isEqualTo(namiMember.getDateOfBirth().toLocalDate());
        assertThat(domain.getRank()).isEqualTo(namiMember.getRank().toDomain());
        assertThat(domain.getGender()).isEqualTo(namiMember.getGender().toDomain());
    }
}
