package de.stinner.anmeldetool.hexagonal.domain.ports.api;

import java.util.List;

public interface UserService {

    List<String> getRolesForSubject(String subject);
}
