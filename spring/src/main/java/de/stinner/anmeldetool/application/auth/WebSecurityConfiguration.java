package de.stinner.anmeldetool.application.auth;

import de.stinner.anmeldetool.application.rest.ActuatorEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).and()
                .cors().and()
                .authorizeRequests(req -> req
                        .requestMatchers(
                                ActuatorEndpoints.READINESS,
                                ActuatorEndpoints.LIVENESS
                        ).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic().disable()
                .formLogin().disable()
                .oauth2ResourceServer(config -> config.jwt(jwtConfigurer -> {
                }));

        return http.build();
    }
}
