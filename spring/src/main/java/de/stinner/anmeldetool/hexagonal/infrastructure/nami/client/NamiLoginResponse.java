package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import lombok.Data;

@Data
public class NamiLoginResponse {
    private Integer statusCode;
    private String statusMessage;
}
