package de.stinner.anmeldetool.application.auth;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.auth.service.AuthenticationService;
import de.stinner.anmeldetool.domain.auth.service.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfiguration {
    private final AuthenticationService authenticationService;
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.authorizeHttpRequests()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http.logout().logoutUrl(ApiEndpoints.V1.Auth.LOGOUT);

        http.authorizeHttpRequests()
                .requestMatchers(
                        ApiEndpoints.V1.Auth.REGISTER,
                        ApiEndpoints.V1.Auth.FINISH_REGISTRATION,
                        ApiEndpoints.V1.Auth.FORGOT_PASSWORD,
                        ApiEndpoints.V1.Auth.RESET_PASSWORD,
                        ApiEndpoints.V1.Auth.LOGOUT
                )
                .anonymous()
                .requestMatchers(ApiEndpoints.V1.Auth.LOGIN)
                .authenticated()
                .anyRequest()
                .hasAuthority(Authority.ROLE_USER.toString());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authenticationService);
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }

    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
