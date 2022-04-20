package application;

import java.io.IOException;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import application.controller.ScheduleController;
import application.controller.LoginController;
import application.controller.SignUpController;
import application.controller.ViewController;
import application.controller.CreateController;
import application.model.Database;
import application.model.Accounts;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Syllabye");
        primaryStage.show();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Database db = new Database(Main.class.getResource("/data/syllabyeDB.json"));
        db.readSyllabye();
        if (db.getSyllabye() == null)
            db.resetSyllabye();
        CreateController.setDatabase(db);
        ViewController.setSyllabye(db.getSyllabye());
        ScheduleController.setDatabase(db);
        Accounts ac = new Accounts();
        SignUpController.setAccounts(ac);
        LoginController.setAccounts(ac);
        launch(args);
    }
}
