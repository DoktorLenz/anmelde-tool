package dev.stinner.scoutventure.application.rest;

import dev.stinner.scoutventure.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

class ActuatorIT extends BaseControllerTest {

    @Test
    void readinessEndpointShouldBeReachableWithoutAuth() {
        given().when().get(RestActuatorEndpoints.READINESS)
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    void livenessActuatorShouldBeReachableWithoutAuth() {
        given().when().get(RestActuatorEndpoints.LIVENESS)
                .then().statusCode(HttpStatus.OK.value());
    }
}
