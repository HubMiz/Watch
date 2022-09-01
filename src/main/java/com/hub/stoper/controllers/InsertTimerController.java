package com.hub.stoper.controllers;

import com.hub.stoper.model.DataSource;
import com.hub.stoper.model.Timer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InsertTimerController {

    @FXML
    private TextField insertTimerName;
    @FXML
    private TextField insertTimerHours;
    @FXML
    private TextField insertTimerMinutes;
    @FXML
    private TextField insertTimerSeconds;


    public Timer insertTimer() {

        if(!checkInput()){
            return null;
        }
        String label = insertTimerName.getText();
        int hours = Integer.valueOf(insertTimerHours.getText());
        int minutes = Integer.valueOf(insertTimerMinutes.getText());
        int seconds = Integer.valueOf(insertTimerSeconds.getText());

        if(!DataSource.getInstance().insertTimer(label,hours,minutes,seconds)){
            return null;
        }

        Timer timer = new Timer();
        timer.setName(label);
        timer.setHours(hours);
        timer.setMinutes(minutes);
        timer.setSeconds(seconds);

        return timer;
    }

    public boolean checkData() {

        if(!checkInput()){
            return false;
        }

        int hours = Integer.valueOf(insertTimerHours.getText());
        int minutes = Integer.valueOf(insertTimerMinutes.getText());
        int seconds = Integer.valueOf(insertTimerSeconds.getText());

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
            Integer.parseInt(insertTimerHours.getText());
            Integer.parseInt(insertTimerMinutes.getText());
            Integer.parseInt(insertTimerSeconds.getText());
        }catch (Exception e){
            return false;
        }
        return true;
    }

}
