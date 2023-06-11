package de.stinner.anmeldetool.domain.authorization.userroles.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRolesController {

    @GetMapping(ApiEndpoints.V1.Auth.USERROLES)
    public ResponseEntity<List<String>> getUserRoles(Authentication authentication) {
        List<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(userRoles);
    }
}
