package com.seniordesigndbgt.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
@Table(name="views")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 3, max = 40)
    @Column(name = "columnOne")
    private ArrayList<Module> columnOne;

    @NotNull
    @Size(min = 3, max = 40)
    @Column(name = "columnTwo")
    private ArrayList<Module> columnTwo;

    @NotNull
    @Size(min = 3, max = 40)
    @Column(name = "columnThree")
    private ArrayList<Module> columnThree;

    public View(User user, String name, ArrayList<Module> columnOne, ArrayList<Module> columnTwo, ArrayList<Module> columnThree) {
        this.user = user;
        this.name = name;
        this.columnOne = columnOne;
        this.columnTwo = columnTwo;
        this.columnThree = columnThree;
    }

    public User getUsername() {
        return user;
    }

    public void setUsername(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Module> getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(ArrayList<Module> columnOne) {
        this.columnOne = columnOne;
    }

    public ArrayList<Module> getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(ArrayList<Module> columnTwo) {
        this.columnTwo = columnTwo;
    }

    public ArrayList<Module> getColumnThree() {
        return columnThree;
    }

    public void setColumnThree(ArrayList<Module> columnThree) {
        this.columnThree = columnThree;
    }
}
