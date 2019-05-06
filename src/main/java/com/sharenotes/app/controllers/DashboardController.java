package com.sharenotes.app.controllers;

import com.sharenotes.app.controllers.utils.AuthHelper;
import com.sharenotes.app.models.messages.Message;
import com.sharenotes.app.models.note.Note;
import com.sharenotes.app.services.userNote.UserNoteService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;

/**
 * user dashboard controller
 * @author Guy Komari 13/04/2019.
 */
@Controller
@AllArgsConstructor
public class DashboardController {
    private final UserNoteService userNoteService;
    private final AuthHelper authHelper;
    @Qualifier("noteDateComparator") private final Comparator noteDateComparator;

    /**
     * get the user dashboard - i.e user notes sorted by @{@link com.sharenotes.app.utils.note.NoteDateComparator}
     * @return the dashboard view
     */
    @GetMapping(value = "/dashboard")
    public ModelAndView getDashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        List<Note> notes = userNoteService.getAllUserNotes(authHelper.getAuthenticatedUser().getUsername());
        notes.sort(noteDateComparator);
        modelAndView.addObject("notes", notes);
        return modelAndView;
    }

    /**
     * get user dashboard with error message
     * @param errorMessage - error message to be displayed to the user
     * @return
     */
    @NotNull
    public ModelAndView getDashboardWithErrorMessage(String errorMessage) {
        ModelAndView modelAndView = getDashboard();
        modelAndView.addObject("message", new Message(errorMessage));
        return modelAndView;
    }
}
