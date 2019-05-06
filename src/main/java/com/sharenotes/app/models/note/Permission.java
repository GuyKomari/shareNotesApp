package com.sharenotes.app.models.note;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User permission for a note
 */
@Getter
@AllArgsConstructor
public enum Permission {
    ADMIN("Full control"),
    READ("Read only"),
    MODIFY("Read & Write");

    private String description;
}
