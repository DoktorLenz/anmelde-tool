package de.stinner.anmeldetool.domain.ports.spi;

import de.stinner.anmeldetool.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberRepository {
    void saveNamiMembers(List<NamiMember> namiMembers);

    List<NamiMember> getNamiMembers();
}
