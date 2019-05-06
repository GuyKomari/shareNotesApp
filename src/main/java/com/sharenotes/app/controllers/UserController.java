package com.sharenotes.app.controllers;

import com.sharenotes.app.controllers.utils.AuthHelper;
import com.sharenotes.app.models.user.User;
import com.sharenotes.app.services.email.EmailService;
import com.sharenotes.app.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * User controller
 * @author Guy Komari 30/03/2019.
 */
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final AuthHelper authHelper;

    /**
     * get change password view
     * @return change password view
     */
    @GetMapping(value = "/password")
    public ModelAndView changePassword() {
        return new ModelAndView("changePassword");
    }

    /**
     * change user password
     * @param user - to change password for
     * @return changePassword view
     */
    @PostMapping(value = "/password")
    public ModelAndView changePassword(@Valid User user) {
        ModelAndView modelAndView = new ModelAndView("changePassword");
        User userByEmail = userService.findUserByEmail(user.getEmail());
        User userByUsername = userService.findUserByUsername(user.getUsername());
        if ((userByEmail == null || userByUsername == null) || !userByEmail.equals(userByUsername)) {
            modelAndView.addObject("InvalidUser", true);
        } else {
            userService.changePassword(user, user.getPassword());
            modelAndView.addObject("validUser", true);
            emailService.onPasswordChanged(userByUsername);
        }
        return modelAndView;
    }

    /**
     * delete user account
     * @return login view
     */
    @GetMapping(value = "/user/delete")
    public ModelAndView deleteUser() {
        User user = authHelper.getAuthenticatedUser();
        userService.deleteUser(user);
        return new ModelAndView("login");
    }
}
