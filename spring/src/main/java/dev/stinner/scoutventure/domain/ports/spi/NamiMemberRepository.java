package dev.stinner.scoutventure.domain.ports.spi;

import dev.stinner.scoutventure.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberRepository {
    void saveNamiMembers(List<NamiMember> namiMembers);

    List<NamiMember> getNamiMembers();
}
