package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class NamiApiEndpoints {
    static final String LOGIN = "/ica/rest/nami/auth/manual/sessionStartup";

    static final String LOGOUT = "/ica/rest/nami/auth/logout";

    static String allMembersOfGrouping(String groupId) {
        return "/ica/rest/nami/mitglied/filtered-for-navigation/gruppierung/gruppierung/" + groupId + "/flist";
    }
}
