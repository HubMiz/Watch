package com.hub.stoper.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Alarm {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty userId;

    private SimpleIntegerProperty year;
    private SimpleIntegerProperty month;
    private SimpleIntegerProperty day;

    private SimpleIntegerProperty hour;
    private SimpleIntegerProperty minute;
    private SimpleIntegerProperty seconds;

    private SimpleIntegerProperty repeatableId;


    public Alarm(){
        id = new SimpleIntegerProperty();
        userId = new SimpleIntegerProperty();

        year = new SimpleIntegerProperty();
        month = new SimpleIntegerProperty();
        day = new SimpleIntegerProperty();

        hour = new SimpleIntegerProperty();
        minute = new SimpleIntegerProperty();
        seconds = new SimpleIntegerProperty();

        repeatableId =new SimpleIntegerProperty();
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

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public int getMonth() {
        return month.get();
    }

    public SimpleIntegerProperty monthProperty() {
        return month;
    }

    public void setMonth(int month) {
        this.month.set(month);
    }

    public int getDay() {
        return day.get();
    }

    public SimpleIntegerProperty dayProperty() {
        return day;
    }

    public void setDay(int day) {
        this.day.set(day);
    }

    public int getHour() {
        return hour.get();
    }

    public SimpleIntegerProperty hourProperty() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour.set(hour);
    }

    public int getMinute() {
        return minute.get();
    }

    public SimpleIntegerProperty minuteProperty() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute.set(minute);
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

    public int getRepeatableId() {
        return repeatableId.get();
    }

    public SimpleIntegerProperty repeatableIdProperty() {
        return repeatableId;
    }

    public void setRepeatableId(int repeatableId) {
        this.repeatableId.set(repeatableId);
    }

    public String getDate(){

        return String.format("%d-%d-%d %02d:%02d:%02d",getYear(),getMonth(),getDay(),getHour(),getMinute(),getSeconds());


    }

}
