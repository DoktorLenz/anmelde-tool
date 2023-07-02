package de.stinner.anmeldetool.domain.nami.service;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class NamiApiEndpoints {
    public static final String LOGIN = "/ica/rest/nami/auth/manual/sessionStartup";

    public static String allMembers(String groupId) {
        return "/ica/rest/nami/mitglied/filtered-for-navigation/gruppierung/gruppierung/" + groupId + "/flist";
    }
}
