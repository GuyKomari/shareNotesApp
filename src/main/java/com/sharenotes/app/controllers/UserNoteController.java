package com.sharenotes.app.controllers;

import com.sharenotes.app.controllers.utils.AuthHelper;
import com.sharenotes.app.models.messages.Message;
import com.sharenotes.app.models.note.Permission;
import com.sharenotes.app.models.note.UserNote;
import com.sharenotes.app.services.user.UserService;
import com.sharenotes.app.services.userNote.UserNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * UserNote controller
 * @author Guy Komari 05/04/2019.
 */
@Controller
@RequiredArgsConstructor
public class UserNoteController {
    private final UserService userService;
    private final UserNoteService userNoteService;
    private final DashboardController dashboardController;
    private final AuthHelper authHelper;

    /**
     * get note users management view
     * @param noteId - for the note users
     * @return noteUsers view
     */
    @GetMapping(value = "/note/{id}/users")
    public ModelAndView getNoteUsers(@PathVariable("id") String noteId){
        if(!userNoteService.isUserPermittedToManageNoteUsers(authHelper.getAuthenticatedUser().getUsername(), noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to view/edit the users of this note");
        }
        ModelAndView modelAndView = new ModelAndView("noteUsers");
        modelAndView.addObject("noteId", noteId);
        modelAndView.addObject("userNote", new UserNote());
        List<UserNote> noteUsers = userNoteService.getAllNoteUsers(noteId);
        noteUsers.removeIf(userNote -> userNote.getPermission() == Permission.ADMIN);
        modelAndView.addObject("noteUsers", noteUsers);
        return modelAndView;
    }

    /**
     * add user to a note
     * @param userNote - contains user and permission
     * @param noteId - to add user to
     * @return noteUsers view
     */
    @PostMapping(value = "/note/{noteId}/users")
    public ModelAndView addUserToNote(@Valid UserNote userNote, @PathVariable("noteId") String noteId){
        String currentUsername = authHelper.getAuthenticatedUser().getUsername();
        if(!userNoteService.isUserPermittedToManageNoteUsers(currentUsername, noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to view/edit the users of this note");
        }else if(userService.findUserByUsername(userNote.getUsername())== null){
            ModelAndView modelAndView = getNoteUsers(noteId);
            modelAndView.addObject("message",
                    new Message(String.format("no username - %s", userNote.getUsername())));
            return modelAndView;
        } else if(!userNote.getUsername().equals(currentUsername)){
            userNoteService.addNoteToUser(userNote.getUsername(), noteId,userNote.getPermission());
        }
        return getNoteUsers(noteId);
    }

    /**
     * remove a user from a note
     * @param noteId
     * @param username - to remove
     * @return noteUsers view
     */
    @GetMapping(value = "/note/{noteId}/user/{username}")
    public ModelAndView removeUserFromNote(@PathVariable("noteId") String noteId, @PathVariable("username") String username){
        if(!userNoteService.isUserPermittedToManageNoteUsers(authHelper.getAuthenticatedUser().getUsername(), noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to view/edit the users of this note");
        }
        userNoteService.removeNoteFromUser(noteId, username);
        return getNoteUsers(noteId);
    }
}
