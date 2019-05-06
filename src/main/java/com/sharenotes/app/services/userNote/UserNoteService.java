package com.sharenotes.app.services.userNote;

import com.sharenotes.app.models.note.Note;
import com.sharenotes.app.models.note.Permission;
import com.sharenotes.app.models.note.UserNote;
import com.sharenotes.app.models.user.User;
import com.sharenotes.app.repositories.note.UserNoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserNote service
 * @see UserNote
 * @see UserNoteRepository
 */
@Service
public interface UserNoteService {

    /**
     * Add note to user
     * @param username
     * @param noteId
     * @param permission
     */
    void addNoteToUser(String username, String noteId, Permission permission);

    /**
     * Get all user notes
     * @param username
     * @return
     */
    List<Note> getAllUserNotes(String username);

    /**
     * Get all note users
     * @param noteId
     * @return
     */
    List<UserNote> getAllNoteUsers(String noteId);

    /**
     * Remove note from user
     * @param noteId
     * @param username
     */
    void removeNoteFromUser(String noteId, String username);

    /**
     * Change user note permission
     * @param noteId
     * @param username
     * @param newPermission
     */
    void changeUserNotePermission(String noteId, String username, Permission newPermission);

    /**
     * Delete note from all users
     * @param noteId
     */
    void deleteNoteForAllUsers(String noteId);

    /**
     * Delete user form all notes
     * @param userId
     */
    void deleteUserForAllNotes(String userId);

    /**
     * Delete all userNote records
     */
    void deleteAllUserNotes();

    /**
     * Create user note
     * @param user
     * @param note
     */
    void createUserNote(User user, Note note);

    /**
     * Receive user note
     * @param username
     * @param noteId
     * @return
     */
    UserNote getUserNote(String username, String noteId);

    /**
     * Validate user permission to delete note
     * @param username
     * @param noteId
     * @return
     */
    boolean isUserPermittedToDeleteNote(String username, String noteId);

    /**
     * Validate user permission to edit note
     * @param username
     * @param noteId
     * @return
     */
    boolean isUserPermittedToEditNote(String username, String noteId);

    /**
     * Validate user permission to view note
     * @param username
     * @param noteId
     * @return
     */
    boolean isUserPermittedToViewNote(String username, String noteId);

    /**
     * Validate user permission to manage note users
     * @param username
     * @param noteId
     * @return
     */
    boolean isUserPermittedToManageNoteUsers(String username, String noteId);

    /**
     * set note inUse
     */
    void setInUse(String username, String noteId, boolean inUse);

    /**
     * is note in inUse
     */
    boolean isNoteInUse(String noteId);

    /**
     * is note in use by a specific user
     */
    boolean isNoteIsUserByUser(String username, String noteId);
}
