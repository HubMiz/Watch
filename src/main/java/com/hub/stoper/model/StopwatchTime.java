package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StopwatchTime  {

    private SimpleIntegerProperty hours;
    private SimpleIntegerProperty minutes;
    private SimpleIntegerProperty seconds;
    private SimpleStringProperty time;

    public StopwatchTime(){
        this.hours = new SimpleIntegerProperty(0);
        this.minutes = new SimpleIntegerProperty(0);
        this.seconds = new SimpleIntegerProperty(0);
        this.time = new SimpleStringProperty("00:00:00");
    }

    public void updateTime(){
        if(seconds.get() < 60){
            seconds.set(seconds.get()+1);
        } else if (minutes.get() < 60) {
            seconds.set(0);
            minutes.set(minutes.get()+1);
        } else if (hours.get() < 24) {
            seconds.set(0);
            minutes.set(0);
            hours.set(hours.get()+1);
        } else {
            seconds.set(0);
            minutes.set(0);
            hours.set(0);
        }
        time.set(String.format("%02d",hours.get()) + ":" + String.format("%02d",minutes.get())+ ":" + String.format("%02d",seconds.get()));
    }



    public StringProperty getTime() {
        return time;
    }
}
