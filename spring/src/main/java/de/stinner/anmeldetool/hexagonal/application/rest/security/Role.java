package de.stinner.anmeldetool.hexagonal.application.rest.security;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class Role {
    public static final String VERIFIED = "VERIFIED";
    public static final String ADMIN = "ADMIN";

    public static final List<String> SUPERUSER = List.of(VERIFIED, ADMIN);
}
