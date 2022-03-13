package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;

import java.io.IOException;

import application.model.Syllabus;
import application.model.Semester;
import application.model.Database;

public class CreateController {
    @FXML
    private TextField courseSubject;
    @FXML
    private TextField courseName;
    @FXML
    private TextField courseNumber;
    @FXML
    private TextField professorName;
    @FXML
    private MenuButton semester;
    @FXML
    private TextField year;

    @FXML
    private Button previousScene;

    private static Database db;

    public void onSave() {
        Syllabus s = new Syllabus(
                courseName.getText(),
                Integer.parseInt(courseNumber.getText()),
                courseSubject.getText(), 
                professorName.getText(), 
                Semester.valueOf(semester.getText().toUpperCase()),
                Integer.parseInt(year.getText()));
        getDatabase().add(s);
        getDatabase().write();
        clickToPreviousPane();
    }

    public void clickToPreviousPane() {
        Stage stage = (Stage) previousScene.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
    }

    public void setSemesterToSpring(ActionEvent event) {
        semester.setText("Spring");
    }

    public void setSemesterToMay(ActionEvent event) {
        semester.setText("May");
    }

    public void setSemesterToSummer(ActionEvent event) {
        semester.setText("Summer");
    }

    public void setSemesterToWinter(ActionEvent event) {
        semester.setText("Winter");
    }

    public void setSemesterToFall(ActionEvent event) {
        semester.setText("Fall");
    }

    public static Database getDatabase() {
        return db;
    }

    public static void setDatabase(Database db) {
        CreateController.db = db;
    }
}
