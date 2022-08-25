package com.hub.stoper.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {


    @FXML
    private BorderPane main;

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
}