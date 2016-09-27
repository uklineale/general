package com.seniordesigndbgt.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Table(name="module")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @JoinColumn(name = "source")
    private String title;

    @NotNull
    @Size(min = 3, max = 200)
    @Column(name = "url")
    private String apiEndpoint;

    @NotNull
    @Size(min = 3, max = 200)
    @Column(name = "fragment")
    private String fragment;

    public Module() {}

    public Module(String title, String apiEndpoint, String fragment) {
        this.title = title;
        this.apiEndpoint = apiEndpoint;
        this.fragment = fragment;
    }

    public Module(String title, String apiEndpoint) {
        this.title = title;
        this.apiEndpoint = apiEndpoint;
        this.fragment = "fragments/" + apiEndpoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }
}
