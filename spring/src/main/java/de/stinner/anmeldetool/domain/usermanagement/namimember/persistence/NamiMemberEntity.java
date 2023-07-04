package de.stinner.anmeldetool.domain.usermanagement.namimember.persistence;

import de.stinner.anmeldetool.domain.shared.model.Gender;
import de.stinner.anmeldetool.domain.shared.model.Rank;
import de.stinner.anmeldetool.domain.usermanagement.namimember.api.models.NamiMemberDto;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "nami_members")
public class NamiMemberEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private Integer memberId;

    private String firstname;

    private String lastname;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    private Rank rank;
    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    private Gender gender;


    public NamiMemberDto toDto() {
        NamiMemberDto dto = new NamiMemberDto();
        dto.setMemberId(memberId);
        dto.setFirstname(firstname);
        dto.setLastname(lastname);
        dto.setRank(rank);
        dto.setGender(gender);
        return dto;
    }
}