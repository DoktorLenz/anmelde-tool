package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientNamiMember {
    @JsonProperty("entries_mitgliedsNummer")
    private Long memberId;
    @JsonProperty("entries_vorname")
    private String firstname;
    @JsonProperty("entries_nachname")
    private String lastname;
    @JsonProperty("entries_geburtsDatum")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;
    @JsonProperty("entries_stufe")
    private ClientRank rank;
    @JsonProperty("entries_geschlecht")
    private ClientGender gender;

    public NamiMember toDomain() {
        return new NamiMember(
                memberId,
                firstname,
                lastname,
                dateOfBirth.toLocalDate(),
                rank.toDomain(),
                gender.toDomain()
        );
    }
}
