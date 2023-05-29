module com.example.gestiune_buguri {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestiune_buguri to javafx.fxml;
    //exports com.example.gestiune_buguri;
    exports com.example.gestiune_buguri.controllers;
    opens com.example.gestiune_buguri.controllers to javafx.fxml;
}