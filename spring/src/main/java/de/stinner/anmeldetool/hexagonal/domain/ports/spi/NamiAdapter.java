package de.stinner.anmeldetool.hexagonal.domain.ports.spi;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;

import java.util.List;

public interface NamiAdapter {
    List<NamiMember> getAllMembersOfGrouping(String username, String password, String groupingId);
}
