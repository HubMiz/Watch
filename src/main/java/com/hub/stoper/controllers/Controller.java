package com.hub.stoper.controllers;


import com.hub.stoper.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

//Todo load once more alarms and delete and create new timeline for their alarms
//Todo Refactor Code
//Todo Allow adding alarms
public class Controller {

    @FXML
    private BorderPane main;
    @FXML
    private GridPane mainCenterGridPane;

    private TilePane tilePaneTimers;

    private Timeline watchTime;
    private Timeline alarmTime;

    private List<Alarm> alarmList;

    public void initialize(){
        main.getStylesheets().add(getClass().getResource("styles/menu.css").toString());
        loadAlarms();
        mainDisplayAlarms();
    }

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
            switch (mainCenterGridPane.getId()){
                case "alarm-grid":
                    loadAlarms();
                    mainDisplayAlarms();
                    break;
                case "timer-grid":
                    mainDisplayTimers();
                    break;
                case "stopwatch-grid":
                    mainDisplayStopWatch();
                    break;
                default:
                    break;
            }
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            controller.selectedItem();
            System.out.println("Canceled");
        }
    }

    @FXML
    public void mainDisplayAlarms(){
        clearCenterUI();

        //Clearing grid pane
        clearCenterUI();

        //Creating rows and columns constraint
        RowConstraints center = new RowConstraints();
        center.setVgrow(Priority.ALWAYS);
        center.setValignment(VPos.TOP);

        RowConstraints bottom = new RowConstraints();


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setHalignment(HPos.LEFT);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.RIGHT);
        col2.setHgrow(Priority.SOMETIMES);

        mainCenterGridPane.getRowConstraints().add(center);
        mainCenterGridPane.getRowConstraints().add(bottom);

        mainCenterGridPane.getColumnConstraints().add(col1);
        mainCenterGridPane.getColumnConstraints().add(col2);

        //Adding tilePane to gridPane
        tilePaneTimers = new TilePane();
        tilePaneTimers.setAlignment(Pos.CENTER);
        tilePaneTimers.setPadding(new Insets(20,20,20,20));
        tilePaneTimers.setHgap(20);
        tilePaneTimers.setVgap(20);

        //Adding UI elements
        alarmList.forEach((alarm -> tilePaneTimers.getChildren().add(createAlarmUI(alarm))));
        Button insertButton = new Button();


        GridPane.setMargin(insertButton,new Insets(0,15,15,0));
        insertButton.setGraphic(new ImageView(new Image(getClass().getResource("/com/hub/stoper/img/add.png").toString())));
        insertButton.setOnAction((ActionEvent event) -> insertAlarm());

        mainCenterGridPane.add(tilePaneTimers,0,0);
        mainCenterGridPane.add(insertButton,1,1);


        //Adding styles
        mainCenterGridPane.setId("alarm-grid");
        tilePaneTimers.setId("alarm-pane");
        mainCenterGridPane.getStylesheets().add(getClass().getResource("/com/hub/stoper/controllers/styles/alarm.css").toString());
    }
    @FXML
    public void mainDisplayTimers(){

        //Clearing grid pane
        clearCenterUI();


        //Creating rows and columns constraint
        RowConstraints center = new RowConstraints();
        center.setVgrow(Priority.ALWAYS);
        center.setValignment(VPos.TOP);

        RowConstraints bottom = new RowConstraints();


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col1.setHalignment(HPos.LEFT);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.RIGHT);
        col2.setHgrow(Priority.SOMETIMES);

        mainCenterGridPane.getRowConstraints().add(center);
        mainCenterGridPane.getRowConstraints().add(bottom);

        mainCenterGridPane.getColumnConstraints().add(col1);
        mainCenterGridPane.getColumnConstraints().add(col2);

        //Adding tilePane to gridPane
        tilePaneTimers = new TilePane();
        tilePaneTimers.setAlignment(Pos.CENTER);
        tilePaneTimers.setPadding(new Insets(20,20,20,20));
        tilePaneTimers.setHgap(20);
        tilePaneTimers.setVgap(20);

        //Creating add button
        Button addButton = new Button();
        GridPane.setMargin(addButton,new Insets(0,15,15,0));
        addButton.setGraphic(new ImageView(new Image(getClass().getResource("/com/hub/stoper/img/add.png").toString())));
        addButton.setOnAction((ActionEvent event) -> insertTimer());

        //Accessing list of timers for current user
        List<Timer> timerList = DataSource.getInstance().getUserTimers();
        timerList.forEach((timer -> tilePaneTimers.getChildren().add(createTimerUI(timer))));

        //Setting id for css

        //Adding Ui elements
        mainCenterGridPane.add(tilePaneTimers,0,0);
        mainCenterGridPane.add(addButton,1,1);

        mainCenterGridPane.setId("timer-grid");
        mainCenterGridPane.getStylesheets().add(getClass().getResource("styles/timer-selection.css").toString());
    }
    @FXML
    public void mainDisplayStopWatch(){
        //Clearing gridPane from elements
        clearCenterUI();

        //Creating Row and Column Constraints
        // center + col1 is for timer
        // bottom + col2 is for save button
        RowConstraints center = new RowConstraints();
        center.setValignment(VPos.BOTTOM);
        center.setVgrow(Priority.ALWAYS);

        RowConstraints bottom = new RowConstraints();

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
        WatchStopwatch watchStopwatch = new WatchStopwatch();
        clock.textProperty().bind(watchStopwatch.getTime());//bind data to stopwatch string property

        //Creating timeline to update UI in real time
        watchTime = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> watchStopwatch.updateTime()));
        watchTime.setCycleCount(Timeline.INDEFINITE);



        //Creating start stop buttons
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button saveButton = new Button();

        buttonHBox.getChildren().add(0,startButton);
        buttonHBox.getChildren().add(1,stopButton);

        GridPane.setMargin(saveButton,new Insets(0,15,15,0));//Setting margins for save button
        saveButton.setGraphic(new ImageView(new Image(getClass().getResource("/com/hub/stoper/img/download.png").toString())));

        //Setting button actions
        startButton.setOnAction((ActionEvent event) -> watchTime.play());
        stopButton.setOnAction((ActionEvent event) -> watchTime.stop());
        saveButton.setOnAction((ActionEvent event ) -> {
            watchTime.stop();
            saveStopWatchTime(watchStopwatch);
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
    public void saveStopWatchTime(WatchStopwatch watch){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Save Time");
        dialog.initOwner(main.getScene().getWindow());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("saveStopwatch.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        SaveStopwatchController controller = loader.getController();
        controller.setInfo(watch);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.insertTime(watch);
        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            System.out.println("Canceled");
        }

    }

    private VBox createTimerUI(Timer timer ){

        //Creating UI elements for timer
        VBox timerUI = new VBox();
        timerUI.setSpacing(5);
        timerUI.setAlignment(Pos.CENTER);

        //Creating elements
        Label name = new Label();
        Label time = new Label();
        Button startButton = new Button("Start");
        ContextMenu deleteMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete");

        deleteMenu.getItems().add(delete);

        //Setting UI values
        name.setText(timer.getName());
        String timeText = String.format("%02d",timer.getHours()) + ":" + String.format("%02d",timer.getMinutes())+ ":" + String.format("%02d",timer.getSeconds());
        time.setText(timeText);

        //Event handler for start
        //Todo create separate method for it during refactoring
        startButton.setOnAction((ActionEvent event) ->{
            clearCenterUI();

            //Todo think about better way to do that
            RowConstraints center = new RowConstraints();
            center.setValignment(VPos.BOTTOM);
            center.setVgrow(Priority.ALWAYS);

            ColumnConstraints col1 = new ColumnConstraints();
            col1.setHalignment(HPos.CENTER);
            col1.setHgrow(Priority.ALWAYS);

            WatchTimer watchTimer = new WatchTimer();
            watchTimer.initializeWatchTimer(timer.getHours(),timer.getMinutes(),timer.getSeconds());


            Label clock = new Label();
            clock.setId("timer-clock");
            clock.textProperty().bind(watchTimer.getTime());//bind data to watch string property

            watchTime = new Timeline();
            watchTime.getKeyFrames().add(new KeyFrame(Duration.seconds(1), actionEvent -> {
                if(watchTimer.getTime().get().equals("00:00:00")){
                    watchTime.stop();
                    Platform.runLater(() -> createAlarmSound("Your timer ended"));
                }
                watchTimer.updateTime();
            }));
            watchTime.setCycleCount(Timeline.INDEFINITE);
            watchTime.play();

            mainCenterGridPane.add(clock,0,0);
            mainCenterGridPane.setAlignment(Pos.CENTER);
            mainCenterGridPane.setId("timer-grid");
            mainCenterGridPane.getStylesheets().add(getClass().getResource("styles/timer-selectedclock.css").toString());

        });

        //Event Handler for delete
        delete.setOnAction((ActionEvent event) ->{
            DataSource.getInstance().deleteTimerByID(timer.getId());
            mainDisplayTimers();
        });


        //
        name.setContextMenu(deleteMenu);
        time.setContextMenu(deleteMenu);
        startButton.setContextMenu(deleteMenu);
        //Adding elements to timer
        timerUI.getChildren().add(name);
        timerUI.getChildren().add(time);
        timerUI.getChildren().add(startButton);

        //Setting id
        timerUI.setId("timer-timer");
        name.setId("timer-timer-name");
        time.setId("timer-timer-time");
        startButton.setId("timer-timer-start-button");


        return timerUI;
    }

    private void clearCenterUI(){
        mainCenterGridPane.getChildren().clear();
        mainCenterGridPane.getRowConstraints().clear();
        mainCenterGridPane.getColumnConstraints().clear();
        mainCenterGridPane.getStylesheets().clear();
        try{
            watchTime.stop();
        }catch (Exception e){
            watchTime = null;
        }
    }

    //Todo Create one method for both insert --> maybe interface?
    private void insertAlarm(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Insert Alarm");
        dialog.initOwner(main.getScene().getWindow());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("insertAlarm.fxml"));

        try{
            dialog.getDialogPane().setContent(loader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw new Error("Insert Failed");
        }

        InsertAlarmController controller = loader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        while (true){
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(controller.checkData()){
                    Alarm alarm = controller.insertAlarm();
                    if(alarm != null){
                        tilePaneTimers.getChildren().add(createAlarmUI(alarm));
                        alarmList.add(alarm);
                        if(alarmList.size() == 1){
                            checkAlarms();
                        }
                    }
                    break;
                }else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Invalid data");
                    error.showAndWait().ifPresent(response -> {
                        if(response == ButtonType.OK){
                        }
                    });
                }

            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("Canceled");
                break;
            }
        }


    }

    private void insertTimer(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Insert Timer");
        dialog.initOwner(main.getScene().getWindow());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("insertTimer.fxml"));

        try{
            dialog.getDialogPane().setContent(loader.load());
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw new Error("Insert Failed");
        }

        InsertTimerController controller = loader.getController();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        while (true) {
            //Todo try to change optional result to lambda expression
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(controller.checkData()){
                    Timer timer = controller.insertTimer();
                    if(timer != null){
                        tilePaneTimers.getChildren().add(createTimerUI(timer));
                    }
                    break;
                }else {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("Invalid data");
                    error.showAndWait().ifPresent(response -> {
                        if(response == ButtonType.OK){
                        }
                    });
                }

            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("Canceled");
                break;
            }
        }

    }

    private VBox createAlarmUI(Alarm alarm){

        //Creating vbox

        VBox alarmVbox = new VBox();

        //Creating vbox elements
        Label name = new Label();
        Label date = new Label();//;

        name.setAlignment(Pos.CENTER);
        date.setAlignment(Pos.CENTER);
        name.setTextAlignment(TextAlignment.CENTER);

        name.setText("Alarm");
        date.setText(alarm.getDate());

        alarmVbox.getChildren().add(name);
        alarmVbox.getChildren().add(date);

        alarmVbox.setId("alarm-alarm");
        date.setId("alarm-alarm-date");
        name.setId("alarm-alarm-name");

        return alarmVbox;

    }

    private void createAlarmSound(String info){
        Alert showAlarm = new Alert(Alert.AlertType.INFORMATION);

        showAlarm.setTitle("Alarm");
        showAlarm.setContentText(info);

        Media media = new Media(getClass().getResource("/com/hub/stoper/controllers/alarm/alarm.mp3").toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        player.play();

        Optional<ButtonType> result = showAlarm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            player.stop();
        }
//        showAlarm.showAndWait();
    }

    private boolean checkIfTime(Alarm alarm, ZonedDateTime time){
        if(alarm.getYear() != time.getYear()) return false;
        if(alarm.getMonth() != time.getMonthValue()) return false;
        if(alarm.getDay() != time.getDayOfMonth()) return false;
        if(alarm.getHour() != time.getHour()) return false;
        if(alarm.getMinute() != time.getMinute()) return false;
        if(alarm.getSeconds() != time.getSecond()) return false;
        return true;
    }

    private boolean isOutdated(Alarm alarm){
        ZoneId id = ZoneId.systemDefault();

        LocalDate date = LocalDate.of(alarm.getYear(),alarm.getMonth(),alarm.getDay());
        LocalTime time = LocalTime.of(alarm.getHour(),alarm.getMinute(),alarm.getSeconds());

        ZonedDateTime currentTimeZoned = ZonedDateTime.now(id);
        ZonedDateTime alarmTime = ZonedDateTime.of(date,time,id);

        return alarmTime.isBefore(currentTimeZoned);
    }


    private void checkAlarms() {
        alarmTime = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            ZoneId id = ZoneId.systemDefault();
            Instant currentTime = Instant.now();

            ZonedDateTime time = currentTime.atZone(id);
            alarmList.forEach( (alarm) -> {
                if(checkIfTime(alarm,time)){
                    Platform.runLater(() -> createAlarmSound("Alarm for: " + alarm.getDate()));
                }
            });

        }));
        alarmTime.setCycleCount(Timeline.INDEFINITE);
        alarmTime.play();
    }

    private void loadAlarms(){
        alarmList = new ArrayList<>();

        List<Alarm> allAlarmsList = DataSource.getInstance().getUserAlarms();
        allAlarmsList.forEach(alarm -> {
            if(isOutdated(alarm)){
                DataSource.getInstance().deleteAlarmsById(alarm.getId());
            }else {
                alarmList.add(alarm);
            }
        });
        if(alarmList.size() > 0) {
            checkAlarms();
        }
    }
}