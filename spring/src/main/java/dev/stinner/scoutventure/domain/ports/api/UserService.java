package dev.stinner.scoutventure.domain.ports.api;

import java.util.List;

public interface UserService {

    List<String> getRolesForSubject(String subject);
}
