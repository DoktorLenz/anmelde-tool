package dev.stinner.scoutventure.application.rest.security;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class Role {
    public static final String VERIFIED = "scoutventure-verified";
    public static final String ADMIN = "scoutventure-admin";

    public static final List<String> SUPERUSER = List.of(VERIFIED, ADMIN);
}
