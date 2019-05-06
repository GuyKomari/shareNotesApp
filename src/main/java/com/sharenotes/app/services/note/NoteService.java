package com.sharenotes.app.services.note;

import com.sharenotes.app.models.note.Note;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * note service
 * @see Note
 * @see com.sharenotes.app.repositories.note.NoteRepository
 */
@Service
public interface NoteService {

    /**
     * receive a note by a note id
     * @param noteId
     * @return
     */
    Note getNoteById(String noteId);

    /**
     * save a note a=in the database
     * @param note
     */
    void saveNote(Note note);

    /**
     * get the last modified date of a note
     * @param noteId
     * @return
     */
    Date getLastDateModified(String noteId);

    /**
     * delete a specific note
     * @param note to delete
     */
    void deleteNote(Note note);

    /**
     * delete all notes
     */
    void deleteAllNotes();

    /**
     * get all notes
     */
    List<Note> getAllNotes();
}
