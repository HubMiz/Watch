package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Watch {

    protected SimpleIntegerProperty hours;
    protected SimpleIntegerProperty minutes;
    protected SimpleIntegerProperty seconds;
    protected SimpleStringProperty time;

    public abstract void updateTime();
    public abstract StringProperty getTime();

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
