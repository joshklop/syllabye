package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.FileNotFoundException;

import application.model.Database;
import application.controller.CreateController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Database db = null;
        try {
            db = new Database(getClass().getResource("/data/syllabyeDB.json").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        CreateController.setDatabase(db);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
            Scene scene = new Scene(root, 300, 250);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
