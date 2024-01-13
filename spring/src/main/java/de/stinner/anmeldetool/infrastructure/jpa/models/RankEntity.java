package de.stinner.anmeldetool.infrastructure.jpa.models;

import de.stinner.anmeldetool.domain.models.Rank;

public enum RankEntity {
    WOELFLING,
    JUNGPFADFINDER,
    PFADFINDER,
    ROVER,
    UNDEFINED;

    public static RankEntity fromDomain(Rank rank) {
        return switch (rank) {
            case WOELFLING -> RankEntity.WOELFLING;
            case JUNGPFADFINDER -> RankEntity.JUNGPFADFINDER;
            case PFADFINDER -> RankEntity.PFADFINDER;
            case ROVER -> RankEntity.ROVER;
            case UNDEFINED -> RankEntity.UNDEFINED;
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
