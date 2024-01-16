package de.stinner.anmeldetool.application.rest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.stinner.anmeldetool.domain.models.Gender;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum GenderDto {
    MALE,
    FEMALE,
    DIVERSE,
    UNDEFINED;

    public static GenderDto fromDomain(Gender gender) {
        return switch (gender) {
            case MALE -> GenderDto.MALE;
            case FEMALE -> GenderDto.FEMALE;
            case DIVERSE -> GenderDto.DIVERSE;
            case UNDEFINED -> GenderDto.UNDEFINED;
        };
    }

    public Gender toDomain(GenderDto genderDto) {
        return switch (genderDto) {
            case MALE -> Gender.MALE;
            case FEMALE -> Gender.FEMALE;
            case DIVERSE -> Gender.DIVERSE;
            case UNDEFINED -> Gender.UNDEFINED;
        };
    }
}
