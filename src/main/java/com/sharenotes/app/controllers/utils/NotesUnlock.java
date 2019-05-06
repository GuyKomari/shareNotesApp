package com.sharenotes.app.controllers.utils;

import com.sharenotes.app.services.note.NoteService;
import com.sharenotes.app.services.userNote.UserNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Unlock notes that haven't been used for a long time
 */
@Component
@RequiredArgsConstructor
public class NotesUnlock {
    private static final int maxTime = 1800000; // 30 minutes - as http session timeout
    private final NoteService noteService;
    private final UserNoteService userNoteService;

    /**
     * Enable unused note if necessary every 10 minutes
     */
    @Scheduled(fixedRate = 600000)
    public void enableNotes() {
        noteService.getAllNotes().stream().filter(n ->
            new Date().getTime() - n.getTimeUpdated().getTime() > maxTime)
                .forEach(note -> userNoteService.getAllNoteUsers(note.getId())
                .forEach(userNote -> userNoteService.setInUse(userNote.getUsername(), note.getId(), false)));
    }
}
