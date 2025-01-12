package dev.stinner.scoutventure.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NamiMember {
    private Long memberId;
    private String firstname;
    private String lastname;

    private LocalDate dateOfBirth;

    private Rank rank;
    private Gender gender;

    private Set<User> userAssignments;
}
