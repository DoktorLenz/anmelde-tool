package de.stinner.anmeldetool.hexagonal.domain.ports.api;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;

import java.util.List;

public interface NamiMemberService {
    List<NamiMember> getNamiMembers();
}
