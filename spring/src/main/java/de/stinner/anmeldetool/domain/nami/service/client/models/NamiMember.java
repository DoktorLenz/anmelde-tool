package de.stinner.anmeldetool.domain.nami.service.client.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.stinner.anmeldetool.domain.shared.model.Gender;
import de.stinner.anmeldetool.domain.shared.model.Rank;
import de.stinner.anmeldetool.domain.usermanagement.namimember.persistence.NamiMemberEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NamiMember {
    @JsonProperty("entries_mitgliedsNummer")
    private Integer memberId;
    @JsonProperty("entries_vorname")
    private String firstname;
    @JsonProperty("entries_nachname")
    private String lastname;
    @JsonProperty("entries_geburtsDatum")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;
    @JsonProperty("entries_stufe")
    private Rank rank;
    @JsonProperty("entries_geschlecht")
    private Gender gender;

    public NamiMemberEntity toNamiMemberEntity() {
        NamiMemberEntity entity = new NamiMemberEntity();
        entity.setMemberId(memberId);
        entity.setFirstname(firstname);
        entity.setLastname(lastname);
        entity.setDateOfBirth(dateOfBirth.toLocalDate());
        entity.setRank(rank);
        entity.setGender(gender);
        return entity;
    }
}
