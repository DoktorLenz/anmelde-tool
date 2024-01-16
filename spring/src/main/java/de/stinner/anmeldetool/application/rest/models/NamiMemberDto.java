package de.stinner.anmeldetool.application.rest.models;

import de.stinner.anmeldetool.domain.models.NamiMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NamiMemberDto {

    private Long memberId;
    private String firstname;
    private String lastname;
    private RankDto rank;
    private GenderDto gender;

    public static NamiMemberDto fromDomain(NamiMember namiMember) {
        return new NamiMemberDto(
                namiMember.getMemberId(),
                namiMember.getFirstname(),
                namiMember.getLastname(),
                RankDto.fromDomain(namiMember.getRank()),
                GenderDto.fromDomain(namiMember.getGender())
        );
    }
}
