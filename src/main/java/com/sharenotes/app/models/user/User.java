/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sharenotes.app.models.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

/**
 * User model
 */
@Getter @Setter
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id private String id;
    @Indexed(unique = true) private String username;
    @Indexed(unique = true) private String email;
    private String password;
    private String fullName;
    private boolean enabled;
    @LastModifiedDate private Date timeUpdated;
    @DBRef private Set<Role> roles;

    public User(String username, String email, String password, String fullName){
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }
}
