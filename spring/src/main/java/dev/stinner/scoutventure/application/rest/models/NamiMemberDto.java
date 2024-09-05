package dev.stinner.scoutventure.application.rest.models;

import dev.stinner.scoutventure.domain.models.NamiMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NamiMemberDto {

    private Long memberId;
    private String firstname;
    private String lastname;
    private RankDto rank;
    private GenderDto gender;
    private Set<UserAssignmentDto> userAssignments;

    public static NamiMemberDto fromDomain(NamiMember namiMember) {
        return new NamiMemberDto(
                namiMember.getMemberId(),
                namiMember.getFirstname(),
                namiMember.getLastname(),
                RankDto.fromDomain(namiMember.getRank()),
                GenderDto.fromDomain(namiMember.getGender()),
                namiMember.getUserAssignments().stream().map(UserAssignmentDto::fromDomain).collect(Collectors.toSet())
        );
    }
}
