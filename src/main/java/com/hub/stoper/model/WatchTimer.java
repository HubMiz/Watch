package com.hub.stoper.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class WatchTimer extends Watch{

    public WatchTimer() {
        super();
    }

    public void initializeWatchTimer(int hours,int minutes,int seconds){

        this.hours.set(hours);
        this.minutes.set(minutes);
        this.seconds.set(seconds);

        time.set(String.format("%02d",this.hours.get()) + ":" + String.format("%02d",this.minutes.get())+ ":" + String.format("%02d",this.seconds.get()));
    }

    @Override
    public void updateTime() {
        if( ( hours.get() == 0 ) && (minutes.get() == 0 ) && ( seconds.get() == 0 )){
            return;
        }
        if(seconds.get() > 0){
            seconds.set(seconds.get() - 1);
        } else if (minutes.get() > 0) {
            minutes.set(minutes.get() -1);
            seconds.set(59);
        } else if (hours.get() > 0) {
            hours.set(hours.get()-1);
            minutes.set(59);
            seconds.set(59);
        }
        time.set(String.format("%02d",hours.get()) + ":" + String.format("%02d",minutes.get())+ ":" + String.format("%02d",seconds.get()));
    }



}
