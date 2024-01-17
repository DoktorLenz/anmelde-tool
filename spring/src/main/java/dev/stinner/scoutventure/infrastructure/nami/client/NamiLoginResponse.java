package dev.stinner.scoutventure.infrastructure.nami.client;

import lombok.Data;

@Data
public class NamiLoginResponse {
    private Integer statusCode;
    private String statusMessage;
}
