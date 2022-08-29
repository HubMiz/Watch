package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StopwatchTime  extends Watch {


    public StopwatchTime(){
        this.hours = new SimpleIntegerProperty(0);
        this.minutes = new SimpleIntegerProperty(0);
        this.seconds = new SimpleIntegerProperty(0);
        this.time = new SimpleStringProperty("00:00:00");
    }
    @Override
    public void updateTime(){
        if(seconds.get() < 59){
            seconds.set(seconds.get()+1);
        } else if (minutes.get() < 59) {
            seconds.set(0);
            minutes.set(minutes.get()+1);
        } else if (hours.get() < 23) {
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

    @Override
    public StringProperty getTime() {
        return time;
    }
}
