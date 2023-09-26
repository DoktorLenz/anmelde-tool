package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import de.stinner.anmeldetool.hexagonal.domain.models.Rank;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum ClientRank {
    WOELFLING("Wölfling"),
    JUNGPFADFINDER("Jungpfadfinder"),
    PFADFINDER("Pfadfinder"),
    ROVER("Rover"),
    UNDEFINED("");

    private final String stringRank;

    ClientRank(String rank) {
        stringRank = rank;
    }

    @JsonValue
    public String getRank() {
        return stringRank;
    }

    public Rank toDomain() {
        return switch (this) {
            case WOELFLING -> Rank.WOELFLING;
            case JUNGPFADFINDER -> Rank.JUNGPFADFINDER;
            case PFADFINDER -> Rank.PFADFINDER;
            case ROVER -> Rank.ROVER;
            case UNDEFINED -> Rank.UNDEFINED;
        };
    }
}
