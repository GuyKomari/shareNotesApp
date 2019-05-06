/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharenotes.app.repositories.user;

import com.sharenotes.app.models.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Role repository
 * @see Role
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * find a role by its name
     * @param role
     * @return role if exists, otherwise null
     */
    Role findByRole(String role);
}
