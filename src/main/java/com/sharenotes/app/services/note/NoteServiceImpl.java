package com.sharenotes.app.services.note;

import com.sharenotes.app.exceptions.NoteNotFoundException;
import com.sharenotes.app.models.note.Note;
import com.sharenotes.app.repositories.note.NoteRepository;
import com.sharenotes.app.services.userNote.UserNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserNoteService userNoteService;

    @Override
    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    @Override
    public Date getLastDateModified(String noteId) {
        Optional<Note> noteRepo = noteRepository.findById(noteId);
        if(noteRepo.isPresent()){
            return noteRepo.get().getTimeUpdated();
        } else {
            throw new NoteNotFoundException("Note not found");
        }
    }

    @Override
    public Note getNoteById(String noteId) {
        Optional<Note> note = noteRepository.findById(noteId);
        if(note.isPresent()){
            return note.get();
        } else{
            throw new NoteNotFoundException("Note not found");
        }
    }

    @Override
    public void deleteNote(Note note) {
        noteRepository.delete(note);
        userNoteService.deleteNoteForAllUsers(note.getId());
    }

    @Override
    public void deleteAllNotes() {
        noteRepository.deleteAll();
    }

    @Override
    public List<Note> getAllNotes() {
        return this.noteRepository.findAll();
    }
}
