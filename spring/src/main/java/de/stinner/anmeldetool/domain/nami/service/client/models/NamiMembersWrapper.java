package de.stinner.anmeldetool.domain.nami.service.client.models;

import lombok.Data;

import java.util.Collection;

@Data
public class NamiMembersWrapper {
    private boolean success;
    private Collection<NamiMember> data;
    private String responseType;
    private int totalEntries;
    private String message;
}
