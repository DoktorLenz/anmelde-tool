package dev.stinner.scoutventure.application.rest.models;

import dev.stinner.scoutventure.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAssignmentDto {

    private String subject;
    private String firstname;
    private String lastname;

    public static UserAssignmentDto fromDomain(User user) {
        return new UserAssignmentDto(user.getSubject(), user.getFirstname(), user.getLastname());
    }
}
