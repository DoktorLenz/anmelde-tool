package de.stinner.anmeldetoolbackend.application.authentication;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

@UtilityClass
public final class UserInfo {

    private static Authentication getUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public static UUID getCurrentUsersId() {
        Object principal = getUserAuthentication().getPrincipal();
        UUID returnValue = null;
        if (principal instanceof Jwt jwt) {
            returnValue = UUID.fromString(jwt.getSubject());
        }

        return returnValue;
    }

    /**
     * Method to get the user ID the request is subject to. Will be extracted from Http-Headers.
     * If not present, {@link UserInfo#getCurrentUsersId()} will be used instead.
     *
     * @return the current Subject's Id.
     */
    public static UUID getSubjectUserId() {
        UUID subjectUserId = getCurrentUsersId();
        return subjectUserId;
    }

}
