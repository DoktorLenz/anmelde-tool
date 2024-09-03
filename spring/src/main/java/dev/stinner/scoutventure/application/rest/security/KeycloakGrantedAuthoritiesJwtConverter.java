package dev.stinner.scoutventure.application.rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class KeycloakGrantedAuthoritiesJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {


    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        return new JwtAuthenticationToken(jwt, extractRoles(jwt));
    }

    private Collection<? extends GrantedAuthority> extractRoles(@NonNull final Jwt jwt) {
        List<String> realmRoles = null;
        Object rolesObject = jwt.getClaimAsMap("realm_access").get("roles");

        if (rolesObject instanceof List<?>) {
            realmRoles = ((List<?>) rolesObject).stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String) role)
                    .toList();
        }

        if (realmRoles != null) {
            return realmRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
        }
        return Collections.emptyList();
    }
}
