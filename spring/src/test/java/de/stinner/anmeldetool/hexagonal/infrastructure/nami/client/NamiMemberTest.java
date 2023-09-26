package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import de.stinner.anmeldetool.hexagonal.domain.models.Gender;
import de.stinner.anmeldetool.hexagonal.domain.models.Rank;
import de.stinner.anmeldetool.hexagonal.infrastructure.jpa.models.NamiMemberEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NamiMemberTest {

    @Test
    void testToEntity() {
        ClientNamiMember namiMember = new ClientNamiMember();
        namiMember.setMemberId(12345);
        namiMember.setFirstname("firstname");
        namiMember.setLastname("lastname");
        namiMember.setDateOfBirth(LocalDateTime.now());
        namiMember.setRank(Rank.JUNGPFADFINDER);
        namiMember.setGender(Gender.DIVERSE);

        NamiMemberEntity entity = namiMember.toNamiMemberEntity();

        assertThat(entity.getMemberId()).isEqualTo(namiMember.getMemberId().longValue());
        assertThat(entity.getFirstname()).isEqualTo(namiMember.getFirstname());
        assertThat(entity.getLastname()).isEqualTo(namiMember.getLastname());
        assertThat(entity.getDateOfBirth()).isEqualTo(namiMember.getDateOfBirth().toLocalDate());
        assertThat(entity.getRank()).isEqualTo(namiMember.getRank());
        assertThat(entity.getGender()).isEqualTo(namiMember.getGender());
    }
}
