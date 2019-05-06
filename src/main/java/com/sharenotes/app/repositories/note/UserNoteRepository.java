/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharenotes.app.repositories.note;

import com.sharenotes.app.models.note.UserNote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * UserNote repository
 * @see UserNote
 */
public interface UserNoteRepository extends MongoRepository<UserNote, String> {
    /**
     * Retrieve all the notes of a specific user
     * @param username
     * @return
     */
    List<UserNote> findByUsername(String username);

    /**
     * Retrieve a user note by its Id
     * @param noteId
     * @return
     */
    List<UserNote> findByNoteId(String noteId);

    /**
     * Retrieve a user note by its userId and noteId
     * @param username
     * @param noteId
     * @return
     */
    UserNote findByUsernameAndNoteId(String username, String noteId);
}