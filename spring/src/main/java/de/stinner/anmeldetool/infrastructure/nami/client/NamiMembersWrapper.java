package de.stinner.anmeldetool.infrastructure.nami.client;

import lombok.Data;

import java.util.List;

@Data
public class NamiMembersWrapper {
    private boolean success;
    private List<ClientNamiMember> data;
    private String responseType;
    private int totalEntries;
    private String message;
}
