package de.stinner.anmeldetool.application.auth;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.domain.auth.service.AuthenticationService;
import de.stinner.anmeldetool.domain.auth.service.Authority;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfiguration {
    private final AuthenticationService authenticationService;
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        http.authorizeHttpRequests()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http.addFilterAfter(new AuthSessionFilter(), AuthorizationFilter.class);

        http.logout().logoutUrl(ApiEndpoints.V1.Auth.LOGOUT);

        http.authorizeHttpRequests()
                .requestMatchers(
                        ApiEndpoints.V1.SESSION,
                        ApiEndpoints.V1.Auth.REGISTER,
                        ApiEndpoints.V1.Auth.FINISH_REGISTRATION,
                        ApiEndpoints.V1.Auth.FORGOT_PASSWORD,
                        ApiEndpoints.V1.Auth.RESET_PASSWORD,
                        ApiEndpoints.V1.Auth.LOGOUT,
                        ApiEndpoints.Actuator.READINESS,
                        ApiEndpoints.Actuator.LIVENESS
                )
                .permitAll()
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

    private static final class CsrfCookieFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(
                HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
        ) throws ServletException, IOException {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            csrfToken.getToken();
            filterChain.doFilter(request, response);
        }
    }

    private static final class AuthSessionFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
            if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
                httpResponse.setHeader("Session-Authenticated", null != httpRequest.getUserPrincipal() ? "true" : "false");
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    String authorities = authentication.getAuthorities().toString();
                    httpResponse.setHeader("Session-Authorities", authorities);
                }
            }

            filterChain.doFilter(request, response);
        }
    }
}
