package com.hub.stoper.controllers;

import com.hub.stoper.model.Alarm;
import com.hub.stoper.model.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;


public class InsertAlarmController {

    @FXML
    private DatePicker insertAlarmDate;
    @FXML
    private TextField insertAlarmHours;
    @FXML
    private TextField insertAlarmMinutes;
    @FXML
    private TextField insertAlarmSeconds;

    public Alarm insertAlarm(){

        if(!checkInput()){
            return null;
        }
        LocalDate date = insertAlarmDate.getValue();
        int hours = Integer.valueOf(insertAlarmHours.getText());
        int minutes = Integer.valueOf(insertAlarmMinutes.getText());
        int seconds = Integer.valueOf(insertAlarmSeconds.getText());

        int alarmId= DataSource.getInstance().insertAlarm(date,hours,minutes,seconds) ;
        if( !( alarmId  >= 1) ) {
            return null;
        }

        Alarm alarm = new Alarm();
        alarm.setId(alarmId);
        alarm.setYear(date.getYear());
        alarm.setMonth(date.getMonthValue());
        alarm.setDay(date.getDayOfMonth());
        alarm.setHour(hours);
        alarm.setMinute(minutes);
        alarm.setSeconds(seconds);
        alarm.setRepeatableId(0);

        return alarm;
    }
    public boolean checkData() {

        if(!checkInput()){
            return false;
        }

        int hours = Integer.valueOf(insertAlarmHours.getText());
        int minutes = Integer.valueOf(insertAlarmMinutes.getText());
        int seconds = Integer.valueOf(insertAlarmSeconds.getText());

        if( ( hours < 0 ) || ( hours > 23 ) ){
            return false;
        }
        if( ( minutes < 0 ) || ( minutes > 59 ) ){
            return false;
        }
        if ( ( seconds < 0 ) && ( seconds > 59 ) ){
            return false;
        }
        return true;
    }

    private boolean checkInput(){
        try{
            Integer.parseInt(insertAlarmHours.getText());
            Integer.parseInt(insertAlarmMinutes.getText());
            Integer.parseInt(insertAlarmSeconds.getText());
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
