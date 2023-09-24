package de.stinner.anmeldetool.hexagonal.infrastructure.jpa.models;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

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
    @Type(PostgreSQLEnumType.class)
    private RankEntity rank;

    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    private GenderEntity gender;

    public static NamiMemberEntity fromNamiMember(NamiMember namiMember) {
        return new NamiMemberEntity(
                namiMember.getMemberId(),
                namiMember.getFirstname(),
                namiMember.getLastname(),
                namiMember.getDateOfBirth(),
                RankEntity.fromDomain(namiMember.getRank()),
                GenderEntity.fromDomain(namiMember.getGender())
        );
    }

    public NamiMember toNamiMember() {
        return new NamiMember(
                memberId,
                firstname,
                lastname,
                dateOfBirth,
                rank.toDomain(),
                gender.toDomain()
        );
    }

}
