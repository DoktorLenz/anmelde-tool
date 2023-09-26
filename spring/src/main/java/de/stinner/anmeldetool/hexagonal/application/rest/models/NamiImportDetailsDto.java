package de.stinner.anmeldetool.hexagonal.application.rest.models;

import lombok.Data;

@Data
public class NamiImportDetailsDto {
    private String username;
    private String password;
    private String groupingId;
}
