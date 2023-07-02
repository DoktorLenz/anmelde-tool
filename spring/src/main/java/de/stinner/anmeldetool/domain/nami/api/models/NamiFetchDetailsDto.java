package de.stinner.anmeldetool.domain.nami.api.models;

import lombok.Data;

@Data
public class NamiFetchDetailsDto {
    private String username;
    private String password;
    private String groupId;
}
