package com.example;

import com.example.databases.*;
import com.example.interfaces.BugRepository;
import com.example.interfaces.ProgramatorRepository;
import com.example.interfaces.TesterRepository;
import com.example.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("C:\\Users\\Esanu\\Downloads\\Ingineria-Sistemelor-Soft-master (1)\\Ingineria-Sistemelor-Soft-master\\Gestiune_Buguri\\bd.config"));
            System.out.println("Properties set. ");
            System.out.println(props);
        }catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        // TesterRepository testerRepository = new TesterDB(props);
        TesterRepository testerRepository = new TesterHibernateDB(props);
        // ProgramatorRepository programatorRepository = new ProgramatorDB(props);
        ProgramatorRepository programatorRepository = new ProgramatorHibernateDB(props);
        // BugRepository bugRepository = new BugDB(props);
        BugRepository bugRepository = new BugHibernateDB(props);

        Service service = new Service(testerRepository, programatorRepository, bugRepository);

        stg = stage;
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/LogIn.fxml"));
        Parent root = fxmlLoader.load();
        LogInController logInController = fxmlLoader.getController();
        logInController.setService(service);

        FXMLLoader programatorLoader = new FXMLLoader(HelloApplication.class.getResource("/ProgramatorWindow.fxml"));
        Parent programatorRoot = programatorLoader.load();
        ProgramatorController programatorController = programatorLoader.getController();
        programatorController.setService(service);

        FXMLLoader testerLoader = new FXMLLoader(HelloApplication.class.getResource("/TesterWindow.fxml"));
        Parent testerRoot = testerLoader.load();
        TesterController testerController = testerLoader.getController();
        testerController.setService(service);

        logInController.setProgramatorController(programatorController);
        logInController.setTesterController(testerController);
        logInController.setParentTester(testerRoot);
        logInController.setParentProgramator(programatorRoot);

        stage.setTitle("Login");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}