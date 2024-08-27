package dev.stinner.scoutventure.domain.services;

import dev.stinner.scoutventure.application.rest.security.Role;
import dev.stinner.scoutventure.domain.models.UserRoles;
import dev.stinner.scoutventure.domain.ports.api.UserService;
import dev.stinner.scoutventure.domain.ports.spi.IamAdapter;
import dev.stinner.scoutventure.domain.ports.spi.UserRolesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserServiceImplTest {
    final String subject = "this_is_a_subject";

    @Test
    void when_userWithRoles_then_returnUsersRoles() {
        UserRoles expectedUserRoles = new UserRoles(subject, List.of(Role.VERIFIED, Role.ADMIN));
        UserRolesRepository repository = mock(UserRolesRepository.class);
        IamAdapter iamAdapter = mock(IamAdapter.class);
        Mockito.when(repository.findBySubject(subject)).thenReturn(expectedUserRoles);


        UserService service = new UserServiceImpl(repository, iamAdapter);
        List<String> roles = service.getRolesForSubject(subject);

        assertThat(roles).containsAll(expectedUserRoles.getRoles());
    }

    @Test
    void when_userWithoutRoles_then_returnEmptyListOfRoles() {
        UserRoles expectedUserRoles = new UserRoles(subject, Collections.emptyList());
        UserRolesRepository repository = mock(UserRolesRepository.class);
        IamAdapter iamAdapter = mock(IamAdapter.class);
        Mockito.when(repository.findBySubject(subject)).thenReturn(expectedUserRoles);

        UserService service = new UserServiceImpl(repository, iamAdapter);
        List<String> roles = service.getRolesForSubject(subject);

        assertThat(roles).isEmpty();
    }

}
