/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharenotes.app.repositories.user;

import com.sharenotes.app.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User repository
 * @see User
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * find user by email
     * @param email
     * @return user if exists, otherwise null
     */
    User findByEmail(String email);

    /**
     * find user by username
     * @param username
     * @return user if exists, otherwise null
     */
    User findByUsername(String username);
}
