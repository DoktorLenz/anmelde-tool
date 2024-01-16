package de.stinner.anmeldetool.domain.ports.api;

import java.util.List;

public interface UserService {

    List<String> getRolesForSubject(String subject);
}
