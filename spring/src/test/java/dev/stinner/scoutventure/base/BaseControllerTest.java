package dev.stinner.scoutventure.base;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest extends BaseIntegrationTest {
    @LocalServerPort
    int port;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setupRestAssured() {
        RestAssured.port = port;

        RestAssuredMockMvc.webAppContextSetup(context);
        RestAssuredMockMvc.postProcessors(csrf().asHeader());
    }
}
