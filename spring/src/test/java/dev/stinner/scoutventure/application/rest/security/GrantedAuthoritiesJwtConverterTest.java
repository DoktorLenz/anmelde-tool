package dev.stinner.scoutventure.application.rest.security;

class GrantedAuthoritiesJwtConverterTest {

//    @Test
//    void when_standardUser_then_getRolesFromService() {
//        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
//        when(userServiceImpl.getRolesForSubject(anyString())).thenReturn(List.of(Role.VERIFIED));
//
//        Jwt token = mock(Jwt.class);
//        when(token.getSubject()).thenReturn("standardUser");
//
//        GrantedAuthoritiesJwtConverter converter = new GrantedAuthoritiesJwtConverter(userServiceImpl);
//
//        JwtAuthenticationToken authenticationToken = converter.convert(token);
//
//        assertThat(authenticationToken).isNotNull();
//
//        List<String> tokenRoles = authenticationToken.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .map(s -> s.substring(5))
//                .toList();
//
//        assertThat(tokenRoles).containsExactly(Role.VERIFIED);
//    }
//
//    @Test
//    void when_superuser_then_superuserRoles() {
//        String superuserSubject = "superuser";
//
//        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
//        when(userServiceImpl.getRolesForSubject(anyString())).thenReturn(List.of(Role.VERIFIED));
//
//        Jwt token = mock(Jwt.class);
//        when(token.getSubject()).thenReturn(superuserSubject);
//
//        GrantedAuthoritiesJwtConverter converter = new GrantedAuthoritiesJwtConverter(userServiceImpl);
//        ReflectionTestUtils.setField(converter, "superuserSubject", superuserSubject);
//
//        JwtAuthenticationToken authenticationToken = converter.convert(token);
//
//        assertThat(authenticationToken).isNotNull();
//
//        List<String> tokenRoles = authenticationToken.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .map(s -> s.substring(5))
//                .toList();
//
//        assertThat(tokenRoles).containsExactlyElementsOf(Role.SUPERUSER);
//    }

}
