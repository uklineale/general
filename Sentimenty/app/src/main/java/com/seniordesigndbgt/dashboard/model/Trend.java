package com.seniordesigndbgt.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Table(name="trend")
public class Trend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;

    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "mentions")
    private String mentions;

    public Trend() {}

    public Trend(String title, String mentions) {
        this.title = title;
        this.mentions = mentions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrendTitle() {
        return title;
    }

    public void setTrendTitle(String title) {
        this.title = title;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }
}
