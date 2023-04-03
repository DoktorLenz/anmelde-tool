package de.stinner.anmeldetool.domain.auth.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.base.BaseControllerTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

public class AuthControllerTest extends BaseControllerTest {

    @Test
    @SneakyThrows
    public void testLogin() {
        performGetRequestWithAuth(ApiEndpoints.V1.Auth.LOGIN, "bar@localhost", "validpassword")
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @SneakyThrows
    public void testLogout() {
        performGetRequest(ApiEndpoints.V1.Auth.LOGOUT).andExpect(status().isNoContent());
        performGetRequest(ApiEndpoints.V1.Auth.LOGIN).andExpect(status().is(401));
    }
}
