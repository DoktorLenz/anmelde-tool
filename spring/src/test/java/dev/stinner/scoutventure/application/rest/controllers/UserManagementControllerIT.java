package dev.stinner.scoutventure.application.rest.controllers;

import dev.stinner.scoutventure.application.rest.RestApiEndpoints;
import dev.stinner.scoutventure.application.rest.models.NamiMemberDto;
import dev.stinner.scoutventure.application.rest.models.NamiMemberUpdateDto;
import dev.stinner.scoutventure.application.rest.models.UserAssignmentDto;
import dev.stinner.scoutventure.application.rest.security.Role;
import dev.stinner.scoutventure.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UserManagementControllerIT extends BaseControllerTest {

    @Test
    @WithMockUser(roles = {Role.ADMIN})
    void when_updatingNamiMember_with_valid_input_then_ResponseEntity_with_204_and_no_content_returned() {
        UserAssignmentDto userAssignmentDto = new UserAssignmentDto("a6a12a9f-8d19-421d-9c6b-c1f0043aa3ad", "admin", "admin");
        NamiMemberUpdateDto updateDto = new NamiMemberUpdateDto(123456L, Set.of(userAssignmentDto));

        given().pathParam("memberId", updateDto.getMemberId())
                .header("Content-Type", "application/json")
                .body(updateDto)
                .when().put(RestApiEndpoints.V1.Usermanagement.NAMI_MEMBER)
                .then().status(HttpStatus.NO_CONTENT);

        List<NamiMemberDto> namiMemberDtos = Arrays.stream(given().when().get(RestApiEndpoints.V1.Usermanagement.NAMI_MEMBERS)
                .then().status(HttpStatus.OK)
                .extract().as(NamiMemberDto[].class)).toList();

        assertThat(namiMemberDtos).hasSize(1);
        assertThat(namiMemberDtos.getFirst().getMemberId()).isEqualTo(123456L);
        assertThat(namiMemberDtos.getFirst().getUserAssignments()).hasSize(1);
    }
}
