package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.application.rest.security.Role;
import de.stinner.anmeldetool.infrastructure.jpa.models.UserRolesEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserRolesRepositoryJpaImplTest {

    final String subject = "this_is_a_subject";

    @Test
    void when_unknownUser_then_addUserWithNoRolesToDb() {
        UserRolesJpaRepository repository = mock(UserRolesJpaRepository.class);
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(UserRolesEntity.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        UserRolesRepositoryJpaSpi repositoryImpl = new UserRolesRepositoryJpaSpi(repository);

        UserRolesEntity entity = repositoryImpl.findBySubject(subject);

        assertThat(entity.getRoles()).isEmpty();
        assertThat(entity.getSubject()).isEqualTo(subject);
        verify(repository, times(1)).save(any(UserRolesEntity.class));
    }

    @Test
    void when_knownUser_then_loadFromDb() {
        UserRolesEntity entityInDb = new UserRolesEntity(subject, List.of(Role.VERIFIED));

        UserRolesJpaRepository repository = mock(UserRolesJpaRepository.class);
        when(repository.findById(anyString())).thenReturn(Optional.of(entityInDb));

        UserRolesRepositoryJpaSpi repositoryImpl = new UserRolesRepositoryJpaSpi(repository);
        UserRolesEntity loadedEntity = repositoryImpl.findBySubject(subject);

        assertThat(loadedEntity).isEqualTo(entityInDb);
    }
}
