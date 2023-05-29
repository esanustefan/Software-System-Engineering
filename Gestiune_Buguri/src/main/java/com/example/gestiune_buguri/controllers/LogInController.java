package com.example.gestiune_buguri.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {


    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField functieTextField;
    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameTextField;


    public void onLogInButtonClick(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String functie = functieTextField.getText();
        if(username.isEmpty() || password.isEmpty() || functie.isEmpty()){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Eroare la LogIn");
            message.setHeaderText("Campurile nu pot fi goale!");
            message.setContentText("Va rugam sa completati toate campurile!");
            message.showAndWait();
        }
        else{
            if(functie.equals("tester")) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Tester.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600 , 400);
                TesterController testerController = fxmlLoader.getController();
                stage.setTitle("Tester: "+ "'" + username + "'");
                stage.setScene(scene);
                stage.show();
                golesteCampurile();
            }
            else if(functie.equals("programator")) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Programator.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600 , 400);
                ProgramatorController programatorController = fxmlLoader.getController();
                stage.setTitle("Programator: "+ "'" + username + "'");
                stage.setScene(scene);
                stage.show();
                golesteCampurile();
            }
            else {
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setTitle("Eroare la LogIn");
                message.setHeaderText("Functia nu este valida!");
                message.setContentText("Va rugam sa introduceti o functie valida (programator / tester) !");
                message.showAndWait();
                functieTextField.clear();
            }

        }
    }

    private void golesteCampurile() {
        usernameTextField.clear();
        passwordTextField.clear();
        functieTextField.clear();
    }
}
