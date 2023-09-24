package de.stinner.anmeldetool.hexagonal.domain.ports.spi;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberRepository {
    List<NamiMember> saveNamiMembers(List<NamiMember> namiMembers);

    List<NamiMember> getNamiMembers();
}
