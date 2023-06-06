package de.stinner.anmeldetool.domain.configuration.api.models;

import de.stinner.anmeldetool.domain.configuration.api.OAuth2Configuration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OAuth2ConfigurationDto {

    @NotBlank
    // Same as issuer-uri
    private String authority;

    @NotEmpty
    private List<@NotBlank String> secureRoutes;

    @NotBlank
    private String clientId;

    public static OAuth2ConfigurationDto of(final OAuth2Configuration oAuth2Configuration) {
        return new OAuth2ConfigurationDto(
                oAuth2Configuration.getIssuerUri(),
                oAuth2Configuration.getSecureRoutes(),
                oAuth2Configuration.getClientId()
        );
    }

}
