module com.hub.stoper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens com.hub.stoper to javafx.fxml;
    opens com.hub.stoper.model to javafx.fxml;
    opens com.hub.stoper.controllers to javafx.fxml,javafx.media;

    exports com.hub.stoper;
    exports com.hub.stoper.model;
    exports com.hub.stoper.controllers;
}