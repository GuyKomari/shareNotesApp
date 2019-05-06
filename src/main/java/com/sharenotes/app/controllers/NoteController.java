package com.sharenotes.app.controllers;

import com.sharenotes.app.controllers.utils.AuthHelper;
import com.sharenotes.app.models.messages.Message;
import com.sharenotes.app.models.note.Note;
import com.sharenotes.app.models.user.User;
import com.sharenotes.app.services.note.NoteService;
import com.sharenotes.app.services.userNote.UserNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Note controller
 */
@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final UserNoteService userNoteService;
    private final AuthHelper authHelper;
    private final DashboardController dashboardController;

    /**
     * create new note
     * @param note - to create
     * @param result
     * @return the createNote view with success or error body
     */
    @PostMapping(value = "/note/createNew")
    public ModelAndView createNote(@Valid Note note, BindingResult result){
        ModelAndView modelAndView = new ModelAndView("createNote");
        User user = authHelper.getAuthenticatedUser();
        modelAndView.addObject("note", note);
        if (!result.hasErrors()) {
            userNoteService.createUserNote(user, note);
            modelAndView.addObject("noteSaved", true);
        } else {
            modelAndView.addObject("noteFailedToSave", true);
        }
        return modelAndView;
    }

    /**
     * get createNote view with @{@link Note} model to be populated
     * @return createNote view
     */
    @GetMapping(value = "/note/createNew")
    public ModelAndView getCreateNoteForm() {
        ModelAndView modelAndView = new ModelAndView("createNote");
        modelAndView.addObject("note", new Note());
        return modelAndView;
    }

    /**
     * get editNote view if permitted
     * @param noteId - to edit
     * @return user dashboard
     */
    @GetMapping(value = "/note/{id}/edit")
    public ModelAndView getEditNoteForm(@PathVariable("id")String noteId){
        String username = authHelper.getAuthenticatedUser().getUsername();
        if(!userNoteService.isUserPermittedToEditNote(username, noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to edit this note");
        }
        if(userNoteService.isNoteInUse(noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note is being used by another user");
        } else {
            userNoteService.setInUse(username, noteId, true);
        }
        ModelAndView modelAndView = new ModelAndView("editNote");
        modelAndView.addObject("note", noteService.getNoteById(noteId));
        return modelAndView;
    }

    /**
     * save note after changes
     * @param note - to edit
     * @return user dashboard
     */
    @PostMapping(value = "/note/{id}/edit", params = "submit=cancel")
    public ModelAndView cancelChanges(@ModelAttribute("note")Note note){
        userNoteService.setInUse(authHelper.getAuthenticatedUser().getUsername(), note.getId(), false);
        return dashboardController.getDashboard();
    }

    /**
     * save note after changes
     * @param note - to edit
     * @return user dashboard
     */
    @PostMapping(value = "/note/{id}/edit", params = "submit=edit")
    public ModelAndView saveNote(@ModelAttribute("note")Note note){
        String username = authHelper.getAuthenticatedUser().getUsername();
        if(!userNoteService.isUserPermittedToEditNote(username, note.getId())){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to edit this note");
        }
        ModelAndView modelAndView = new ModelAndView("editNote");
        modelAndView.addObject("note", note);
        if(!userNoteService.isNoteIsUserByUser(username, note.getId())){
            modelAndView.addObject("message",
                    new Message("The time provided to edit this note has expired. Your changes won't be saved."));
        } else {
            noteService.saveNote(note);
            modelAndView.addObject("noteSaved");
        }

        return modelAndView;
    }

    /**
     * delete note from editNote view
     * @param noteId - of the note to be deleted
     * @return user dashboard view
     */
    @PostMapping(value = "/note/{id}/edit", params = "submit=delete")
    public ModelAndView deleteNoteFromEditView(@PathVariable("id")String noteId){
        return deleteNote(noteId);
    }

    /**
     * delete note from dashboard view
     * @param noteId - of the note to be deleted
     * @return user dashboard view
     */
    @GetMapping(value = "/note/{id}/delete")
    public ModelAndView deleteNoteFromDashboard(@PathVariable("id")String noteId){
        return deleteNote(noteId);
    }

    /**
     * delete note if the user is permitted to delete it
     * @param noteId to delete
     * @return dashboard view
     */
    private ModelAndView deleteNote(String noteId) {
        if(!userNoteService.isUserPermittedToDeleteNote(authHelper.getAuthenticatedUser().getUsername(), noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to delete this note");
        }
        noteService.deleteNote(noteService.getNoteById(noteId));
        return dashboardController.getDashboard();
    }

    /**
     * get note for view only
     * @param noteId - to view
     * @return viewNote view
     */
    @GetMapping(value = "/note/{id}/view")
    public ModelAndView viewNote(@PathVariable("id")String noteId){
        if(!userNoteService.isUserPermittedToViewNote(authHelper.getAuthenticatedUser().getUsername(), noteId)){
            return dashboardController.getDashboardWithErrorMessage(
                    "The note does not exist anymore OR you do not have the appropriate permission to delete this note");
        }
        ModelAndView modelAndView = new ModelAndView("viewNote");
        Note note = noteService.getNoteById(noteId);
        modelAndView.addObject("note", note);
        if(userNoteService.isNoteInUse(noteId)){
            modelAndView.addObject("message",
                    new Message("The note is being used by another user. It's content may not be fully updated"));
        }
        return modelAndView;
    }
}




