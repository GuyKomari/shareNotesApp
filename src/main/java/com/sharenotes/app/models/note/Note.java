package com.sharenotes.app.models.note;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Note Model
 */
@Getter @Setter
@NoArgsConstructor
@ToString
@Document(collection = "note")
public class Note {
    @Id private String id;
    private String title;
    private String description;
    private String content;
    @LastModifiedDate private Date timeUpdated;

    public Note(String title, String description, String content){
        super();
        this.title = title;
        this.description = description;
        this.content = content;
    }
}



