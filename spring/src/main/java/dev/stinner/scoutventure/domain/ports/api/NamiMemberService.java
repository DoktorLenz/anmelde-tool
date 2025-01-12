package dev.stinner.scoutventure.domain.ports.api;

import dev.stinner.scoutventure.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberService {
    List<NamiMember> getNamiMembers();

    NamiMember getNamiMemberById(Long memberId);

    void triggerImport(String username, String password, String groupingId);

    void updateNamiMember(NamiMember namiMember);
}
