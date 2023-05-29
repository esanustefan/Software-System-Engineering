package com.example;

import com.example.observer.BugObserverInterface;
import com.example.observer.BugServiceInterface;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ProgramatorController implements BugObserverInterface {

    @FXML
    private TableColumn<Bug,String> columnDenumire;
    @FXML
    private TableColumn<Bug,String> columnDescriere;
    @FXML
    private TableColumn<Bug,String> columnGradRisc;
    @FXML
    private TableColumn<Bug,String> columnStatus;
    @FXML
    private  CheckBox checkboxNu;
    @FXML
    private  CheckBox checkboxCrescator;
    @FXML
    private  CheckBox checkboxDescrescator;
    @FXML
    private  Button updateButton;
    @FXML
    private TableView<Bug> tableBug;

    @FXML
    private CheckBox checkboxDa;

    private BugServiceInterface service;

    private final ObservableList<Bug> bugsModel = FXCollections.observableArrayList();

    private Programator programator;

    @FXML
    private void onSelectDa(ActionEvent actionEvent) {
        checkboxNu.setSelected(false);
        checkboxDa.setSelected(true);
    }

    public void setService(BugServiceInterface service) {
        this.service = service;
    }


    private void initializeColumns(List<Bug> bugs) {
        bugsModel.setAll(bugs);
        columnDescriere.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescriere()));
        columnDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDenumire()));
        columnGradRisc.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBugRisk()));
        columnStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBugStatus()));
        tableBug.setItems(bugsModel);
    }
    public void initializeTable() throws Exception {
        checkboxDa.setSelected(false);
        checkboxNu.setSelected(true);
        System.out.println("initializeTable");
        tableBug.getItems().clear();
        List<Bug> bugs = service.getAllBugs(this);
        initializeColumns(bugs);
    }

    private int getStatusValue(String status) {
        return switch (status) {
            case "OPEN" -> 0;
            case "SOLVED" -> 1;
            default -> -1;
        };
    }

    private int getRiskValue(String risk) {
        return switch (risk) {
            case "FUNCTIONAL_DEFECTS" -> 0;
            case "PERFORMANCE_DEFECTS" -> 1;
            case "USABILITY_DEFECTS" -> 2;
            case "SECURITY_DEFECTS" -> 3;
            default -> -1;
        };
    }


    public void onSelectCrescator(ActionEvent actionEvent) throws Exception {
        checkboxCrescator.setSelected(true);
        checkboxDescrescator.setSelected(false);
        tableBug.getItems().clear();
        List<Bug> bugs = service.getAllBugs(this);
        bugs.sort(Comparator.comparingInt((Bug bug) -> getRiskValue(bug.getBugRisk()))
                .thenComparing(bug -> getStatusValue(bug.getBugStatus())));
        initializeColumns(bugs);
    }

    public void onSelectDescrescator(ActionEvent actionEvent) throws Exception {
        checkboxCrescator.setSelected(false);
        checkboxDescrescator.setSelected(true);
        tableBug.getItems().clear();
        List<Bug> bugs = service.getAllBugs(this);
        bugs.sort(Comparator.comparingInt((Bug bug) -> getRiskValue(bug.getBugRisk())).reversed()
                .thenComparing(bug -> getStatusValue(bug.getBugStatus())));
        initializeColumns(bugs);
    }

    public void onUpdateButtonClick(ActionEvent actionEvent) {
        try {
            if(checkboxNu.isSelected()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Trebuie sa fii sigur ca rezolvi acest bug!");
                alert.showAndWait();
                return;
            }
            Bug bug = tableBug.getSelectionModel().getSelectedItem();
            if(Objects.equals(bug.getBugStatus(), BugStatus.SOLVED.toString())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Bug-ul a fost deja rezolvat!");
                alert.showAndWait();
                return;
            }
            // allow to update a bug ONLY if the risk is the highest from all the bugs
            if (!checkHighestRisk(bug))  {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Trebuie sa rezolvi bugul cu risk maxim!");
                alert.showAndWait();
                return;
            }
            if (checkboxDa.isSelected()) {
                bug.setBugStatus(BugStatus.SOLVED.toString());
            }
            service.updateBug(bug, this);
            initializeTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkHighestRisk(Bug bug) throws Exception {

        List<Bug> bugs = service.getAllBugs(this);
        List<Bug> newBugs = bugs.stream().filter(bug1 -> bug1.getBugStatus().equals(BugStatus.OPEN.toString()))
                .toList();
        BugRisk highestRisk = BugRisk.FUNCTIONAL_DEFECTS;
        BugRisk bugRisk = null;
        for(Bug b : newBugs) {
            if(Objects.equals(b.getBugRisk(), " FUNCTIONAL_DEFECTS")){
                bugRisk = BugRisk.valueOf(" FUNCTIONAL_DEFECTS");
            }
            else {
                if (Objects.equals(b.getBugRisk(), "PERFORMANCE_DEFECTS")) {
                    bugRisk = BugRisk.valueOf("PERFORMANCE_DEFECTS");
                }
                else if (Objects.equals(b.getBugRisk(), "USABILITY_DEFECTS")) {
                    bugRisk = BugRisk.valueOf("USABILITY_DEFECTS");
                }
                else {
                    bugRisk = BugRisk.valueOf("SECURITY_DEFECTS");
                }
            }
            if (bugRisk.compareTo(highestRisk) > 0) {
                highestRisk = bugRisk;
            }
        }
        System.out.println("Highest risk: " + highestRisk);
        System.out.println("Bug risk: " + bug.getBugRisk());
        return Objects.equals(bug.getBugRisk(), highestRisk.toString());
    }

    public void onSelectNu(ActionEvent actionEvent) {
        checkboxDa.setSelected(false);
        checkboxNu.setSelected(true);
    }

    public void onLogoutButtonClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) tableBug.getScene().getWindow();
            service.logoutProgramator(programator, this);
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProgramator(Programator programator1) {
        this.programator = programator1;
    }
    @Override
    public void updateBug() throws Exception {
        Platform.runLater(() -> {
            try {
                System.out.println("UPDATE TABLE NOTIFY ALL");
                initializeTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
