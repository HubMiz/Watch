package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Watch {

    protected SimpleIntegerProperty hours;
    protected SimpleIntegerProperty minutes;
    protected SimpleIntegerProperty seconds;
    protected SimpleStringProperty time;

    public Watch(){
        this.hours = new SimpleIntegerProperty(0);
        this.minutes = new SimpleIntegerProperty(0);
        this.seconds = new SimpleIntegerProperty(0);
        this.time = new SimpleStringProperty("00:00:00");
    }

    public abstract void updateTime();
    public StringProperty getTime() {
        return time;
    }
    public int getHours() {
        return hours.get();
    }

    public SimpleIntegerProperty hoursProperty() {
        return hours;
    }

    public int getMinutes() {
        return minutes.get();
    }

    public SimpleIntegerProperty minutesProperty() {
        return minutes;
    }

    public int getSeconds() {
        return seconds.get();
    }

    public SimpleIntegerProperty secondsProperty() {
        return seconds;
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }


}
