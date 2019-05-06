package com.sharenotes.app.configurations.auth;

import com.sharenotes.app.consts.Roles;
import com.sharenotes.app.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom Authentication Success Handler - validate if the user is authenticated
 * @see AuthenticationSuccessHandler
 */
@Component
@RequiredArgsConstructor
public class CustomAuthHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        if(!userService.findUserByUsername(authentication.getName()).isEnabled()){
            response.sendRedirect("login?registerError=true");
        } else {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (Roles.USER.equals(auth.getAuthority())) {
                    response.sendRedirect("dashboard");
                }
            }
        }
    }
}
