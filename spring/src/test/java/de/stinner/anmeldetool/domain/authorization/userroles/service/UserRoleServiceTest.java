package de.stinner.anmeldetool.domain.authorization.userroles.service;

import de.stinner.anmeldetool.hexagonal.application.rest.security.Role;
import de.stinner.anmeldetool.hexagonal.domain.service.UserServiceImpl;
import de.stinner.anmeldetool.hexagonal.infrastructure.jpa.models.UserRolesEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserRoleServiceTest {

    @Test
    void when_knownUser_then_getRolesViaRepository() {
        UserRolesEntity entity = new UserRolesEntity();
        entity.setRoles(List.of(Role.VERIFIED));

        UserRolesRepository repository = mock(UserRolesRepository.class);
        when(repository.findById(anyString())).thenReturn(Optional.of(entity));

        UserServiceImpl service = new UserServiceImpl(repository);

        List<String> roles = service.getRolesForSubject("");

        assertThat(roles).containsOnly(Role.VERIFIED);
    }

    @Test
    void when_unknownUser_then_addUserWithNoRoles() {
        UserRolesRepository repository = mock(UserRolesRepository.class);
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(UserRolesEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        UserServiceImpl service = new UserServiceImpl(repository);

        List<String> roles = service.getRolesForSubject("");

        assertThat(roles).isEmpty();
    }

}
