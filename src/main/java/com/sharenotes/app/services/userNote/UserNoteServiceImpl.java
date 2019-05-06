package com.sharenotes.app.services.userNote;

import com.sharenotes.app.models.note.Note;
import com.sharenotes.app.models.note.Permission;
import com.sharenotes.app.models.note.UserNote;
import com.sharenotes.app.models.user.User;
import com.sharenotes.app.repositories.note.UserNoteRepository;
import com.sharenotes.app.services.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserNoteServiceImpl implements UserNoteService {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserNoteRepository userNoteRepository;

    @Override
    public List<Note> getAllUserNotes(String username) {
        List<UserNote> userNotes = userNoteRepository.findByUsername(username);
        List<Note> notes = new ArrayList<>();
        userNotes.forEach(uN -> notes.add(noteService.getNoteById(uN.getNoteId())));
        return notes;
    }

    @Override
    public List<UserNote> getAllNoteUsers(String noteId) {
        return userNoteRepository.findByNoteId(noteId);
    }

    @Override
    public void removeNoteFromUser(String noteId, String username) {
        UserNote userNote = getUserNote(username, noteId);
        userNoteRepository.delete(userNote);
    }

    @Override
    public void changeUserNotePermission(String noteId, String username, Permission newPermission) {
        UserNote userNote = getUserNote(username, noteId);
        userNote.setPermission(newPermission);
        userNoteRepository.save(userNote);
    }

    @Override
    public void addNoteToUser(String username, String noteId, Permission permission) {
        UserNote userNote = userNoteRepository.findByUsernameAndNoteId(username, noteId);
        if (userNote == null) {
            userNote = new UserNote(username, noteId, permission);
        } else {
            userNote.setPermission(permission);
        }
        userNoteRepository.save(userNote);
    }

    @Override
    public void deleteNoteForAllUsers(String noteId) {
        List<UserNote> users = getAllNoteUsers(noteId);
        users.forEach(u -> {
            removeNoteFromUser(noteId, u.getUsername());
        });
    }

    @Override
    public void deleteUserForAllNotes(String username) {
        List<Note> notes = getAllUserNotes(username);
        notes.forEach(n -> {
            removeNoteFromUser(n.getId(), username);
        });
    }

    @Override
    public void deleteAllUserNotes() {
        userNoteRepository.deleteAll();
    }

    @Override
    public void createUserNote(User user, Note note) {
        noteService.saveNote(note);
        addNoteToUser(user.getUsername(), note.getId(), Permission.ADMIN);
    }

    @Override
    public UserNote getUserNote(String username, String noteId) {
        return userNoteRepository.findByUsernameAndNoteId(username, noteId);
    }

    @Override
    public boolean isUserPermittedToDeleteNote(String username, String noteId) {
        UserNote userNote = getUserNote(username, noteId);
        return userNote != null && userNote.getPermission().equals(Permission.ADMIN);
    }

    @Override
    public boolean isUserPermittedToEditNote(String username, String noteId) {
        UserNote userNote = getUserNote(username, noteId);
        return userNote != null && !userNote.getPermission().equals(Permission.READ);
    }

    @Override
    public boolean isUserPermittedToViewNote(String username, String noteId) {
        UserNote userNote = getUserNote(username, noteId);
        return userNote != null;
    }

    @Override
    public boolean isUserPermittedToManageNoteUsers(String username, String noteId) {
        UserNote userNote = getUserNote(username, noteId);
        return userNote != null && userNote.getPermission().equals(Permission.ADMIN);
    }

    @Override
    public void setInUse(String username, String noteId, boolean inUse) {
        UserNote userNote = getUserNote(username, noteId);
        userNote.setInUse(inUse);
        userNoteRepository.save(userNote);
    }

    @Override
    public boolean isNoteInUse(String noteId) {
        return this.getAllNoteUsers(noteId).stream().anyMatch(UserNote::isInUse);
    }

    @Override
    public boolean isNoteIsUserByUser(String username, String noteId) {
        return this.getUserNote(username, noteId).isInUse();
    }
}
