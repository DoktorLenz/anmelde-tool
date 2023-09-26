package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NamiApiEndpointsTest {

    @Test
    void correctNamiLoginPath() {
        Assertions.assertThat(NamiApiEndpoints.LOGIN).isEqualTo("/ica/rest/nami/auth/manual/sessionStartup");
    }

    @Test
    void build_url_with_groupId() {
        String groupId = "12345";
        assertThat(NamiApiEndpoints.allMembersOfGrouping(groupId)).isEqualTo("/ica/rest/nami/mitglied/filtered-for-navigation/gruppierung/gruppierung/" + groupId + "/flist");
    }
}
