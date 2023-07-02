package de.stinner.anmeldetool.domain.usermanagement.namimember.api.models;

import de.stinner.anmeldetool.domain.shared.model.Gender;
import de.stinner.anmeldetool.domain.shared.model.Rank;
import lombok.Data;

@Data
public class NamiMemberDto {
    private Integer memberId;
    private String firstname;
    private String lastname;
    private Rank rank;
    private Gender gender;

}
