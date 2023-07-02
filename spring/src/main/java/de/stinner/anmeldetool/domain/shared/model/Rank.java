package de.stinner.anmeldetool.domain.shared.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Rank {
    WOELFLING("Wölfling"),
    JUNGPFADFINDER("Jungpfadfinder"),
    PFADFINDER("Pfadfinder"),
    ROVER("Rover"),
    UNDEFINED("");

    private final String stringRank;

    Rank(String rank) {
        stringRank = rank;
    }

    @JsonValue
    public String getRank() {
        return stringRank;
    }
}
