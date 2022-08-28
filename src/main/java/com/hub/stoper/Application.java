package com.hub.stoper;

import com.hub.stoper.model.DataSource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 400);
        stage.setTitle("Stoper");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();

        //Loading custom font
        Font.loadFont(getClass().getResource("controllers/styles/clock-font.ttf").toExternalForm(),10);

        DataSource.getInstance().open();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        DataSource.getInstance().close();
    }

    public static void main(String[] args) {
        launch();
    }
}