package de.stinner.anmeldetool.domain.ports.spi;

import de.stinner.anmeldetool.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberRepository {
    List<NamiMember> saveNamiMembers(List<NamiMember> namiMembers);

    List<NamiMember> getNamiMembers();
}
