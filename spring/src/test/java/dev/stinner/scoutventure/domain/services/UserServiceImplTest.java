package dev.stinner.scoutventure.domain.services;

public class UserServiceImplTest {
    final String subject = "this_is_a_subject";

//    @Test
//    void when_userWithRoles_then_returnUsersRoles() {
//        UserRoles expectedUserRoles = new UserRoles(subject, List.of(Role.VERIFIED, Role.ADMIN));
//        UserRolesRepository repository = mock(UserRolesRepository.class);
//        IamAdapter iamAdapter = mock(IamAdapter.class);
//        UserRepository userRepository = mock(UserRepository.class);
//        Mockito.when(repository.findBySubject(subject)).thenReturn(expectedUserRoles);
//
//
//        UserService service = new UserServiceImpl(repository, iamAdapter, userRepository);
//        List<String> roles = service.getRolesForSubject(subject);
//
//        assertThat(roles).containsAll(expectedUserRoles.getRoles());
//    }
//
//    @Test
//    void when_userWithoutRoles_then_returnEmptyListOfRoles() {
//        UserRoles expectedUserRoles = new UserRoles(subject, Collections.emptyList());
//        UserRolesRepository repository = mock(UserRolesRepository.class);
//        IamAdapter iamAdapter = mock(IamAdapter.class);
//        UserRepository userRepository = mock(UserRepository.class);
//        Mockito.when(repository.findBySubject(subject)).thenReturn(expectedUserRoles);
//
//        UserService service = new UserServiceImpl(repository, iamAdapter, userRepository);
//        List<String> roles = service.getRolesForSubject(subject);
//
//        assertThat(roles).isEmpty();
//    }

}
