package com.hub.stoper.controllers;

import com.hub.stoper.model.DataSource;
import com.hub.stoper.model.WatchStopwatch;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class SaveStopwatchController {
    @FXML
    private TextArea saveStopwatchLabel;
    @FXML
    private Text saveStopwatchInfo;

    public void setInfo(WatchStopwatch watch){
        saveStopwatchInfo.setText("Are yo sure you want to save this time: " + watch.getTime().get() + "?");
    }

    public void insertTime(WatchStopwatch watch){

        int seconds = watch.getSeconds();
        int minutes = watch.getMinutes();
        int hours = watch.getHours();

        DataSource.getInstance().insertStopwatchTime(hours,minutes,seconds,saveStopwatchLabel.getText());

    }

}
