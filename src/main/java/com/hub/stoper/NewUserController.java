package com.hub.stoper;

import com.hub.stoper.model.DataSource;
import com.hub.stoper.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

public class NewUserController {

    @FXML
    private TextField newUserNameField;
    @FXML
    public void insertNewUser(){
        List<User> userList = DataSource.getInstance().getUserByName(newUserNameField.getText());

        if(userList.size() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("User with this name already exists");
            alert.show();

            return;
        }

        DataSource.getInstance().insertUser(newUserNameField.getText());

    }

}
