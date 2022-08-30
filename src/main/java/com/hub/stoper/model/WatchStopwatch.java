package com.hub.stoper.model;

public class WatchStopwatch extends Watch {


    public WatchStopwatch(){
        super();
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

}
