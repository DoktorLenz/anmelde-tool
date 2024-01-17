package dev.stinner.scoutventure.application.rest.controllers;

import dev.stinner.scoutventure.application.rest.RestApiEndpoints;
import dev.stinner.scoutventure.application.rest.models.ClientConfigurationDto;
import dev.stinner.scoutventure.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

class ClientConfigurationControllerIT extends BaseControllerTest {
    
    @Test
    void when_getConfiguration_then_ResoinseEntity_with_200_and_propper_content_returned() {
        ClientConfigurationDto dto = given().when().get(RestApiEndpoints.V1.CONFIGURATION)
                .then().status(HttpStatus.OK)
                .extract().as(ClientConfigurationDto.class);

        assertThat(dto.getOauth2Configuration().getAuthority()).isEqualTo("https://oidc-provider.test");
        assertThat(dto.getOauth2Configuration().getClientId()).isEqualTo("1234567890@client");
        assertThat(dto.getOauth2Configuration().getSecureRoutes())
                .containsExactly("/api", "https://example.com");
    }
}
