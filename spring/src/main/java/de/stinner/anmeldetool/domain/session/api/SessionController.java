package de.stinner.anmeldetool.domain.session.api;


import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class SessionController {

    @GetMapping(ApiEndpoints.V1.SESSION)
    public ResponseEntity<Boolean> session(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (null != principal) {
            return new ResponseEntity(true, HttpStatus.OK);
        }
        return new ResponseEntity(false, HttpStatus.OK);
    }
}
