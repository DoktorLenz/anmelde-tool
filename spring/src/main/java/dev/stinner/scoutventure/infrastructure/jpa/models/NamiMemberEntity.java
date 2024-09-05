package dev.stinner.scoutventure.infrastructure.jpa.models;

import dev.stinner.scoutventure.domain.models.NamiMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "nami_members")
@AllArgsConstructor
@NoArgsConstructor
public class NamiMemberEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private Long memberId;

    private String firstname;
    private String lastname;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private RankEntity rank;

    @Enumerated(EnumType.STRING)
    private GenderEntity gender;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "iam_users_nami_members_mapping", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "subject"))
    private Set<UserEntity> userAssignments;

    public static NamiMemberEntity fromNamiMember(NamiMember namiMember) {
        return new NamiMemberEntity(
                namiMember.getMemberId(),
                namiMember.getFirstname(),
                namiMember.getLastname(),
                namiMember.getDateOfBirth(),
                RankEntity.fromDomain(namiMember.getRank()),
                GenderEntity.fromDomain(namiMember.getGender()),
                Set.of()
        );
    }

    public NamiMember toNamiMember() {
        return new NamiMember(
                memberId,
                firstname,
                lastname,
                dateOfBirth,
                rank.toDomain(),
                gender.toDomain(),
                userAssignments.stream().map(UserEntity::toDomain).collect(Collectors.toSet())
        );
    }

}
