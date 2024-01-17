package dev.stinner.scoutventure.domain.ports.spi;

import dev.stinner.scoutventure.domain.models.NamiMember;

import java.util.List;

public interface NamiAdapter {
    List<NamiMember> getAllMembersOfGrouping(String username, String password, String groupingId);
}
