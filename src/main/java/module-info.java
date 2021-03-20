module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires testng;

    opens controllers to javafx.fxml;
    exports controllers;
    exports utils;
}