package dev.stinner.scoutventure.infrastructure.nami.client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRankTest {

    @Test
    void test_string_to_Rank() {
        Assertions.assertThat(ClientRank.WOELFLING.getRank()).isEqualTo("Wölfling");
        assertThat(ClientRank.JUNGPFADFINDER.getRank()).isEqualTo("Jungpfadfinder");
        assertThat(ClientRank.PFADFINDER.getRank()).isEqualTo("Pfadfinder");
        assertThat(ClientRank.ROVER.getRank()).isEqualTo("Rover");
        assertThat(ClientRank.UNDEFINED.getRank()).isEmpty();
    }
}
