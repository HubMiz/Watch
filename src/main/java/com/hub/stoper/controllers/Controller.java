package com.hub.stoper.controllers;


import com.hub.stoper.model.StopwatchTime;
import com.hub.stoper.model.Watch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;

import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane main;

    @FXML
    private GridPane mainCenterGridPane;

    @FXML
    public void addNewUserOnClick() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add new user");
        dialog.initOwner(main.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newUser.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        NewUserController controller = loader.getController();

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.insertNewUser();
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            System.out.println("Canceled");
        }

    }

    @FXML
    public void changeUser() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Change User");
        dialog.initOwner(main.getScene().getWindow());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("changeUser.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ChangeUserController controller = loader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.changeUser();
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            controller.selectedItem();
            System.out.println("Canceled");
        }
    }

    @FXML
    public void mainDisplayAlarms(){
        mainCenterGridPane.getChildren().clear();//Clear GridPane
    }
    @FXML
    public void mainDisplayTimers(){
        mainCenterGridPane.getChildren().clear();//Clear GridPane
    }
    @FXML
    public void mainDisplayStopWatch(){
        //Clearing gridPane from elements
        mainCenterGridPane.getChildren().clear();
        mainCenterGridPane.getRowConstraints().clear();
        mainCenterGridPane.getColumnConstraints().clear();

        //Creating Row and Column Constraints
        // center + col1 is for timer
        // bottom + col2 is for save button
        RowConstraints center = new RowConstraints();
        center.setValignment(VPos.BOTTOM);
        center.setVgrow(Priority.ALWAYS);

        RowConstraints bottom = new RowConstraints();
        center.setValignment(VPos.BOTTOM);
        center.setVgrow(Priority.SOMETIMES);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.CENTER);
        col1.setHgrow(Priority.ALWAYS);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.RIGHT);
        col2.setHgrow(Priority.SOMETIMES);

        mainCenterGridPane.getRowConstraints().add(center);
        mainCenterGridPane.getRowConstraints().add(bottom);

        mainCenterGridPane.getColumnConstraints().add(col1);
        mainCenterGridPane.getColumnConstraints().add(col2);


        //Creating UI elements
        Label clock = new Label();//Creating label
        VBox vboxForClockAndButtons = new VBox();
        HBox buttonHBox = new HBox();

        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setPadding(new Insets(20,10,10,10));
        buttonHBox.setSpacing(20);

        clock.setMinWidth(Region.USE_PREF_SIZE);
        clock.setMinHeight(Region.USE_PREF_SIZE);

        vboxForClockAndButtons.setAlignment(Pos.CENTER);

        //Creating stopwatch
        StopwatchTime stopwatchTime = new StopwatchTime();
        clock.textProperty().bind(stopwatchTime.getTime());//bind data to stopwatch string property

        //Creating timeline to update UI in real time
        final Timeline changeTime = new Timeline(new KeyFrame(Duration.seconds(1),actionEvent -> stopwatchTime.updateTime()));
        changeTime.setCycleCount(Timeline.INDEFINITE);



        //Creating start stop buttons
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button saveButton = new Button();

        buttonHBox.getChildren().add(0,startButton);
        buttonHBox.getChildren().add(1,stopButton);

        GridPane.setMargin(saveButton,new Insets(0,15,15,0));//Setting margins for save button
        saveButton.setGraphic(new ImageView(new Image(getClass().getResource("/com/hub/stoper/img/download.png").toString())));

        //Setting button actions
        startButton.setOnAction((ActionEvent event) ->changeTime.play());
        stopButton.setOnAction((ActionEvent event) -> changeTime.stop());
        saveButton.setOnAction((ActionEvent event ) -> {
            changeTime.stop();
            saveStopWatchTime(stopwatchTime);
        });


        //Adding elements to UI elements
        vboxForClockAndButtons.getChildren().add(0,clock);

        vboxForClockAndButtons.getChildren().add(1,buttonHBox);

        mainCenterGridPane.add(vboxForClockAndButtons,0,0);
        mainCenterGridPane.add(saveButton,1,1);

        //Setting ids for CSS
        stopButton.setId("stopwatch-stop-button");
        startButton.setId("stopwatch-start-button");
        saveButton.setId("stopwatch-save-button");

        clock.setId("stopwatch-clock");
        mainCenterGridPane.setId("stopwatch-grid");

        //Adding css
        mainCenterGridPane.setAlignment(Pos.CENTER);
        GridPane.setMargin(mainCenterGridPane, new Insets(5, 0, 5, 0));
        mainCenterGridPane.getStylesheets().add(getClass().getResource("styles/stoper.css").toString());

    }


    public void saveStopWatchTime(StopwatchTime watch){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Save Time");
        dialog.initOwner(main.getScene().getWindow());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("saveStopwatch.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SaveStopwatchController controller = loader.getController();
        controller.setInfo(watch);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.insertTime(watch);
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            System.out.println("Canceled");
        }

    }
    //Todo create function to create dialog panes
}