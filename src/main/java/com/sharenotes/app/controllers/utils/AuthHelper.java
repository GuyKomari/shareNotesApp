package com.sharenotes.app.controllers.utils;

import com.sharenotes.app.models.user.User;
import com.sharenotes.app.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * User authentication helper
 * @author Guy Komari 06/04/2019.
 */
@Component
@RequiredArgsConstructor
public class AuthHelper {
    private final UserService userService;

    /**
     * get the authenticated user for the current http request
     * @return the User as represent in the database
     * @see User
     */
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }
}
