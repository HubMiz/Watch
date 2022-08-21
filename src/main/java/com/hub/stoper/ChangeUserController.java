package com.hub.stoper;

import com.hub.stoper.model.DataSource;
import com.hub.stoper.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;

import java.util.stream.Collectors;

public class ChangeUserController {

    @FXML
    private ListView<User> changeUserListView;

    public void initialize(){
        ObservableList<User> observableList = FXCollections.observableList(DataSource.getInstance().getUsers());
        changeUserListView.setCellFactory( userListView -> new ListCell<User>(){
            @Override
            protected void updateItem(User item, boolean empty){
                if(item == null || empty || item.getName() == null){
                    setText(null);
                }else{
                    setText(item.getName());
                }
            }
        });
        changeUserListView.setItems(observableList);
        changeUserListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        changeUserListView.getSelectionModel().selectFirst();
    }


    public void changeUser(){
        int id = changeUserListView.getSelectionModel().getSelectedItem().getId();
        System.out.println(id);
        DataSource.getInstance().updateCurrentUser(id);
    }

    public void selectedItem(){
        System.out.println(changeUserListView.getSelectionModel().getSelectedItem().getName());
    }


}
