package de.stinner.anmeldetool.application.auth;

import de.stinner.anmeldetool.domain.authorization.userroles.service.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class GrantedAuthoritiesJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final UserRolesService userRolesService;

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt source) {
        final Set<String> roles = userRolesService.getRolesFromSubject(source.getSubject());

        final Set<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(s -> new SimpleGrantedAuthority("ROLE_" + s)).collect(Collectors.toSet());

        return new JwtAuthenticationToken(source, grantedAuthorities);
    }
}
