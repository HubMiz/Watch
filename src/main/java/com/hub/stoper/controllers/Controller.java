package com.hub.stoper.controllers;


import com.hub.stoper.model.StopwatchTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        mainCenterGridPane.getChildren().clear();//Clear GridPane
        final Label clock = new Label();//Creating label

        StopwatchTime stopwatchTime = new StopwatchTime();

        clock.textProperty().bind(stopwatchTime.getTime());

        Timeline changeTime = new Timeline(new KeyFrame(Duration.seconds(1),actionEvent -> stopwatchTime.updateTime()));
        changeTime.setCycleCount(Timeline.INDEFINITE);
        changeTime.play();

        mainCenterGridPane.add(clock,0,0);
    }
}