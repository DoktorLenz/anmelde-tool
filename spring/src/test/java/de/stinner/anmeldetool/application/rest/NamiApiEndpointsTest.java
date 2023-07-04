package de.stinner.anmeldetool.application.rest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NamiApiEndpointsTest {

    @Test
    void correctNamiLoginPath() {
        assertThat(NamiApiEndpoints.LOGIN).isEqualTo("/ica/rest/nami/auth/manual/sessionStartup");
    }

    @Test
    void build_url_with_groupId() {
        String groupId = "12345";
        assertThat(NamiApiEndpoints.allMembers(groupId)).isEqualTo("/ica/rest/nami/mitglied/filtered-for-navigation/gruppierung/gruppierung/" + groupId + "/flist");
    }
}
