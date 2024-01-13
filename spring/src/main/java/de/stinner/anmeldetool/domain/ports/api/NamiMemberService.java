package de.stinner.anmeldetool.domain.ports.api;

import de.stinner.anmeldetool.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberService {
    List<NamiMember> getNamiMembers();

    void triggerImport(String username, String password, String groupingId);
}
