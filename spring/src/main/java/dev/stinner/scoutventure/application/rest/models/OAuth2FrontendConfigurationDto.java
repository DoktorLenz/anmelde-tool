package dev.stinner.scoutventure.application.rest.models;

import dev.stinner.scoutventure.application.rest.security.OAuth2FrontendConfiguration;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OAuth2FrontendConfigurationDto {

    @NotBlank
    // Same as issuer-uri
    private String authority;

    @NotBlank
    private String clientId;

    public static OAuth2FrontendConfigurationDto of(final OAuth2FrontendConfiguration oAuth2Configuration) {
        return new OAuth2FrontendConfigurationDto(
                oAuth2Configuration.getIssuerUri(),
                oAuth2Configuration.getClientId()
        );
    }

}
