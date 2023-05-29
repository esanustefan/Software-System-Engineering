package com.example;

import com.example.observer.BugServiceInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    @FXML
    private RadioButton RadioProgramator;

    @FXML
    private RadioButton RadioVerificator;
    @FXML
    private PasswordField passwordTextField;
//    @FXML
//    private TextField functieTextField;
    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameTextField;

    private BugServiceInterface service;

    private ProgramatorController programatorController;
    private TesterController testerController;
    private Parent programatorRoot;
    private Parent testerRoot;
    public void setService(BugServiceInterface service) {
        this.service = service;
    }
    public void setParentProgramator(Parent p) {
        this.programatorRoot = p;
    }
    public void setParentTester(Parent p) {
        this.testerRoot = p;
    }


    public void onLogInButtonClick(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
//        String functie = functieTextField.getText();
        if(username.isEmpty() || password.isEmpty()){
            Alert message = new Alert(Alert.AlertType.ERROR);
            message.setTitle("Eroare la LogIn");
            message.setHeaderText("Campurile nu pot fi goale!");
            message.setContentText("Va rugam sa completati toate campurile!");
            message.showAndWait();
        }
        else {
            // System.out.println("LogIn: " + username + " " + password + " " + functie);
            try {
                if(RadioVerificator.isSelected()) {
                    Tester tester = new Tester(username, password);
                    System.out.println(tester);
                    Tester tester1 = service.loginTester(tester, testerController);
                    System.out.println("dupa service login: " + tester1);
                    if(tester1 == null) {
                        Alert message = new Alert(Alert.AlertType.ERROR);
                        message.setTitle("Eroare la LogIn");
                        message.setHeaderText("Cont inexistent!");
                        message.setContentText("Specificati alte date de logare!");
                        message.showAndWait();
                    }
                    else {
                        Stage stage = new Stage();
                        Scene scene = new Scene(testerRoot, 600 , 400);
                        stage.setTitle("Tester: "+ "'" + username + "'");
                        stage.setScene(scene);
                        testerController.setTester(tester1);
                        testerController.setService(service);
                        testerController.initializeTable();
                        stage.show();
                        golesteCampurile();
                    }
                }
                else if(RadioProgramator.isSelected()) {
                    Programator programator = new Programator(username, password);
                    System.out.println(programator);
                    Programator programator1 = service.loginProgramator(programator, programatorController);
                    System.out.println("dupa service login: " + programator1);
                    if(programator1 == null) {
                        Alert message = new Alert(Alert.AlertType.ERROR);
                        message.setTitle("Eroare la LogIn");
                        message.setHeaderText("Cont inexistent!");
                        message.setContentText("Specificati alte date de logare!");
                        message.showAndWait();
                    }
                    else {
                        Stage stage = new Stage();
                        Scene scene = new Scene(programatorRoot, 600 , 400);
                        stage.setTitle("Programator: "+ "'" + username + "'");
                        stage.setScene(scene);
                        programatorController.setProgramator(programator1);
                        programatorController.setService(service);
                        programatorController.initializeTable();
                        stage.show();
                        golesteCampurile();
                    }
                }
//                else {
//                    Alert message = new Alert(Alert.AlertType.ERROR);
//                    message.setTitle("Eroare la LogIn");
//                    message.setHeaderText("Functia nu este valida!");
//                    message.setContentText("Va rugam sa introduceti o functie valida (programator / tester) !");
//                    message.showAndWait();
//                    functieTextField.clear();
//                }
            }  catch (Exception e) {
                Alert message = new Alert(Alert.AlertType.ERROR);
                message.setTitle("Eroare la LogIn");
                message.setHeaderText(e.getMessage());
                message.setContentText("Specificati alte date de logare!");
                message.showAndWait();
                golesteCampurile();
            }
        }
    }

    private void golesteCampurile() {
        usernameTextField.clear();
        passwordTextField.clear();
//        functieTextField.clear();
    }

    public void setProgramatorController(ProgramatorController programatorController) {
        this.programatorController = programatorController;
    }

    public void setTesterController(TesterController testerController) {
        this.testerController = testerController;
    }
}
