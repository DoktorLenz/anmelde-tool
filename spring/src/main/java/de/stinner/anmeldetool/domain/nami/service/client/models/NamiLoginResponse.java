package de.stinner.anmeldetool.domain.nami.service.client.models;

import lombok.Data;

@Data
public class NamiLoginResponse {
    private Integer statusCode;
    private String statusMessage;
}
