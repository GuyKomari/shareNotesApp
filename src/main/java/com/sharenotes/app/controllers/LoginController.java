package com.sharenotes.app.controllers;

import com.sharenotes.app.models.user.User;
import com.sharenotes.app.services.kafka.KafkaProducerService;
import com.sharenotes.app.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Base64;

/**
 * Application login and signUp controller
 */
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final KafkaProducerService kafkaProducerService;

    /**
     * get the login view (also the home view)
     * @return login view
     */
    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * get the signUp view with a User model
     * @return the signUp view
     */
    @GetMapping(value = "/signup")
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    /**
     * create a new user, and return the signUp view with success or error message
     * @param user - to be created
     * @return the signUp view with relevant message
     */
    @PostMapping (value = "/signup")
    public ModelAndView createNewUser(@Valid User user) {
        ModelAndView modelAndView = new ModelAndView("signup");
        // each user should be unique by username and email
        if (userService.isUserExists(user)) {
            modelAndView.addObject("userFailedToCreate", true);
        } else { // valid user then create
            userService.saveUser(user);
            modelAndView.addObject("userCreated", true);
            sendEventUserCreated(user);
        }
        return modelAndView;
    }

    /**
     * register a user
     * @param encodedId - user id encoded
     * @return
     */
    @GetMapping (value = "/register/{id}")
    public ModelAndView registerUser(@PathVariable("id") String encodedId) {
        userService.enableUser(userService.findUserById(new String(Base64.getDecoder().decode(encodedId))));
        return login();
    }

    /**
     * send an event to Apache Kafka that a new user has been created successfully
     * @param user - the created user
     */
    private void sendEventUserCreated(User user) {
        this.kafkaProducerService.onNewUser(user); }
}

