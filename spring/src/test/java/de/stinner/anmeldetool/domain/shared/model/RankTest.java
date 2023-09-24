package de.stinner.anmeldetool.domain.shared.model;

import de.stinner.anmeldetool.hexagonal.domain.models.Rank;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankTest {

    @Test
    void test_string_to_Rank() {
        assertThat(Rank.WOELFLING.getRank()).isEqualTo("Wölfling");
        assertThat(Rank.JUNGPFADFINDER.getRank()).isEqualTo("Jungpfadfinder");
        assertThat(Rank.PFADFINDER.getRank()).isEqualTo("Pfadfinder");
        assertThat(Rank.ROVER.getRank()).isEqualTo("Rover");
        assertThat(Rank.UNDEFINED.getRank()).isEmpty();
    }
}
