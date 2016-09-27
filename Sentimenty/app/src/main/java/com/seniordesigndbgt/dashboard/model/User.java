package com.seniordesigndbgt.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(min =3, max = 50)
    @Column(name = "default_view")
    private String defaultView;

    public User() {}

    public User(String username) {
        this.username = username;
        this.defaultView = "employee";
    }

    public User(String username, String defaultView) {
        this.username = username;
        this.defaultView = defaultView;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDefaultView() {
        return defaultView;
    }

    public void setDefaultView(String defaultView) {
        this.defaultView = defaultView;
    }
}
