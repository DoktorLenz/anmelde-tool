package de.stinner.anmeldetool.domain.nami.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NamiMembersWrapper {
    @JsonProperty("success")
    private boolean success;
    private List<NamiMember> data;
    @JsonProperty("responseType")
    private ResponseType responseType;
    private int totalEntries;
    private String message;
}
