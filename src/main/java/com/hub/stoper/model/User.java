package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {



    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty currentUser;

    public User() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.currentUser = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getCurrentUser() {
        return currentUser.get();
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser.set(currentUser);
    }
}
