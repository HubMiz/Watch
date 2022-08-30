package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Timer {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty userId;

    private SimpleIntegerProperty hours;
    private SimpleIntegerProperty minutes;
    private SimpleIntegerProperty seconds;

    private SimpleStringProperty name;


    public Timer(){
        this.id = new SimpleIntegerProperty();
        this.userId = new SimpleIntegerProperty();

        this.hours = new SimpleIntegerProperty();
        this.minutes = new SimpleIntegerProperty();
        this.seconds = new SimpleIntegerProperty();

        this.name = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public int getHours() {
        return hours.get();
    }

    public SimpleIntegerProperty hoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
    }

    public int getMinutes() {
        return minutes.get();
    }

    public SimpleIntegerProperty minutesProperty() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes.set(minutes);
    }

    public int getSeconds() {
        return seconds.get();
    }

    public SimpleIntegerProperty secondsProperty() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds.set(seconds);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
