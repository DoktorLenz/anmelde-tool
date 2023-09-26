package de.stinner.anmeldetool.domain.configuration.api;

import de.stinner.anmeldetool.base.BaseControllerTest;
import de.stinner.anmeldetool.domain.configuration.api.models.ConfigurationDto;
import de.stinner.anmeldetool.hexagonal.application.rest.RestApiEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationControllerIT extends BaseControllerTest {

    @Test
    void when_getConfiguration_then_ResponseEntity_with_200_and_propper_content_returned() {
        ConfigurationDto dto = given().when().get(RestApiEndpoints.V1.CONFIGURATION)
                .then().status(HttpStatus.OK)
                .extract().as(ConfigurationDto.class);

        assertThat(dto.getOauth2Configuration().getAuthority()).isEqualTo("https://oidc-provider.test");
        assertThat(dto.getOauth2Configuration().getClientId()).isEqualTo("1234567890@client");
        assertThat(dto.getOauth2Configuration().getSecureRoutes())
                .containsExactly("/api", "https://example.com");
    }
}
