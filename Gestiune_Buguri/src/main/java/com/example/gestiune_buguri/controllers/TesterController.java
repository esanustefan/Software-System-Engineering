package com.example.gestiune_buguri.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class TesterController {


    @FXML
    private Button adaugaBugButton;

    @FXML
    private CheckBox checkboxDa;

    @FXML
    private TextField denumireTextField;

    @FXML
    private TextField descriereTextField;

    @FXML
    private TextField gradRiscTextField;

    @FXML
    private TextField usernameProgramatorTextField;

    @FXML
    void onAdaugaBugClick(ActionEvent event) {

    }


    public void onSelectDa(ActionEvent actionEvent) {
        if(checkboxDa.isSelected()){
            usernameProgramatorTextField.setDisable(false);
        }
        else{
            usernameProgramatorTextField.setDisable(true);
        }
    }
}
