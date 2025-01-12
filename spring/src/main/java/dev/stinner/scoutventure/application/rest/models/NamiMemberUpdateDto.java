package dev.stinner.scoutventure.application.rest.models;

import dev.stinner.scoutventure.domain.models.NamiMember;
import dev.stinner.scoutventure.domain.models.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class NamiMemberUpdateDto {

    @NotNull
    private Long memberId;
    @NotNull
    private Set<UserAssignmentDto> userAssignments;

    public NamiMember applyToDomainModel(NamiMember namiMember, List<User> users) {
        if (!namiMember.getMemberId().equals(this.memberId)) {
            throw new IllegalArgumentException("Can not apply the update dto to a unmatched nami member. Matched by memberId.");
        }

        namiMember.setUserAssignments(users.stream()
                .filter(user -> userAssignments.stream()
                        .anyMatch(uA -> uA.getSubject().equals(user.getSubject()))
                )
                .collect(Collectors.toSet())
        );
        return namiMember;
    }
}
