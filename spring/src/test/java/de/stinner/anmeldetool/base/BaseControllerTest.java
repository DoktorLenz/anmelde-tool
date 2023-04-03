package de.stinner.anmeldetool.base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest extends BaseIntegrationTest {

    protected static RequestSpecification baseRequest;

    @LocalServerPort
    int port;

    @BeforeEach
    public void setupRestAssured() {
        RestAssured.port = port;
        baseRequest = RestAssured.given().contentType(ContentType.JSON);
    }
}
