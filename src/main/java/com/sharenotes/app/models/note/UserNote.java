package com.sharenotes.app.models.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * User note model
 */
@Getter @Setter
@NoArgsConstructor
@Document(collection = "userNote")
@CompoundIndexes({
        @CompoundIndex(name = "username_noteId", def = "{'username' : 1, 'noteId' : 1}", unique = true)
})
public class UserNote {
    @Id private String id;
    @Field("username")
    private String username;
    @Field("noteId")
    private String noteId;
    private Permission permission;
    private boolean inUse;

    public UserNote(String username, String noteId, Permission permission) {
        super();
        this.username = username;
        this.noteId = noteId;
        this.permission = permission;
        this.inUse = false;
    }
}
