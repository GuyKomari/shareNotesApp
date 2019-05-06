/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharenotes.app.repositories.note;

import com.sharenotes.app.models.note.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Note repository
 * @see Note
 */
public interface NoteRepository extends MongoRepository<Note, String> {
}
