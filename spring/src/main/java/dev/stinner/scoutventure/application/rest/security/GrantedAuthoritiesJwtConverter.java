package dev.stinner.scoutventure.application.rest.security;

import dev.stinner.scoutventure.domain.ports.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class GrantedAuthoritiesJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final UserService userService;
    @Value("${scoutventure.oidc.superuser-sub}")
    private String superuserSubject;

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt source) {
        final String subject = source.getSubject();
        final List<String> roles;

        if (subject.equals(superuserSubject)) {
            roles = Role.SUPERUSER;
        } else {
            roles = userService.getRolesForSubject(source.getSubject());
        }

        final Set<GrantedAuthority> grantedAuthorities = roles.stream()
                .map(s -> new SimpleGrantedAuthority("ROLE_" + s)).collect(Collectors.toSet());

        return new JwtAuthenticationToken(source, grantedAuthorities);
    }
}
