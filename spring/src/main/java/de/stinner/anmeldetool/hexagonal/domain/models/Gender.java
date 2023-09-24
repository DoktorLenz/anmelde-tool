package de.stinner.anmeldetool.hexagonal.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
    MALE("männlich"),
    FEMALE("weiblich"),
    DIVERSE("divers"),
    UNDEFINED("keine Angabe");

    private final String stringGender;

    Gender(String gender) {
        stringGender = gender;
    }

    @JsonValue
    public String getGender() {
        return stringGender;
    }
}
