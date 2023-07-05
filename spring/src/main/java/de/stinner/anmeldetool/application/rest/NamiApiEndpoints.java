package de.stinner.anmeldetool.application.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class NamiApiEndpoints {
    public static final String LOGIN = "/ica/rest/nami/auth/manual/sessionStartup";

    public static final String LOGOUT = "/ica/rest/nami/auth/logout";

    public static String allMembersOfGrouping(String groupId) {
        return "/ica/rest/nami/mitglied/filtered-for-navigation/gruppierung/gruppierung/" + groupId + "/flist";
    }
}
