package dev.stinner.scoutventure.application.rest.models;

import dev.stinner.scoutventure.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String subject;
    private String firstname;
    private String lastname;
    private String username;
    private String email;

    public static UserDto fromDomain(User user) {
        return new UserDto(
                user.getSubject(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
