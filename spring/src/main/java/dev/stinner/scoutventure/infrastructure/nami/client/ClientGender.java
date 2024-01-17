package dev.stinner.scoutventure.infrastructure.nami.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.stinner.scoutventure.domain.models.Gender;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum ClientGender {
    MALE("männlich"),
    FEMALE("weiblich"),
    DIVERSE("divers"),
    UNDEFINED("keine Angabe");

    private final String stringGender;

    ClientGender(String gender) {
        stringGender = gender;
    }

    @JsonValue
    public String getGender() {
        return stringGender;
    }

    public Gender toDomain() {
        return switch (this) {
            case MALE -> Gender.MALE;
            case FEMALE -> Gender.FEMALE;
            case DIVERSE -> Gender.DIVERSE;
            case UNDEFINED -> Gender.UNDEFINED;
        };
    }
}
