package com.sharenotes.app.utils.note;

import com.sharenotes.app.models.note.Note;

import java.util.Comparator;

/**
 * Compare notes by the updated time
 */
public class NoteDateComparator implements Comparator<Note> {
    @Override
    public int compare(Note o1, Note o2) {
        return  (o1.getTimeUpdated().compareTo(o2.getTimeUpdated()))> 0 ? -1 : 1;
    }
}
