package de.stinner.anmeldetool.application.rest.models;

import de.stinner.anmeldetool.domain.models.Rank;

public enum RankDto {
    WOELFLING,
    JUNGPFADFINDER,
    PFADFINDER,
    ROVER,
    UNDEFINED;

    public static RankDto fromDomain(Rank rank) {
        return switch (rank) {
            case WOELFLING -> RankDto.WOELFLING;
            case JUNGPFADFINDER -> RankDto.JUNGPFADFINDER;
            case PFADFINDER -> RankDto.PFADFINDER;
            case ROVER -> RankDto.ROVER;
            case UNDEFINED -> RankDto.UNDEFINED;
        };
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
