package com.seniordesigndbgt.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name="twitter")
public class Twitter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "text")
    private String text;

    @NotNull
    @Size(min=1, max = 750)
    @Column(name="url")
    private String url;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name="author")
    private String author;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name="image")
    private String image;

    @NotNull
    @Column(name="created")
    private Timestamp created;

    public Twitter(String author, String text, String url, String image, Timestamp created) {
        this.author = author;
        this.text = text;
        this.url = url;
        this.image = image;
        this.created = created;
    }

    public Twitter() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() { return url; }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString(){
        if(author == null || text == null)
            return null;
        else
            return "@" + author + " - " + text;
    }
}
