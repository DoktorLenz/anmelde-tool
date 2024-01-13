package de.stinner.anmeldetool.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
