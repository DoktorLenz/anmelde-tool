package de.stinner.anmeldetool.application.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GrantedAuthoritiesJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt source) {
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        return new JwtAuthenticationToken(source, grantedAuthorities);
    }
}
