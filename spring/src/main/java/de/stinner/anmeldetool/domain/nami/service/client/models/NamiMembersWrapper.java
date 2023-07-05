package de.stinner.anmeldetool.domain.nami.service.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
public class NamiMembersWrapper {
    @JsonProperty("success")
    private boolean success;
    private Collection<NamiMember> data;
    @JsonProperty("responseType")
    private ResponseType responseType;
    private int totalEntries;
    private String message;
}
