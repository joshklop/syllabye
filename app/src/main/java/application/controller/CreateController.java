package application.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

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

    private static Database db;

    public void onSave() throws IOException {
        Syllabus s = new Syllabus(
                courseName.getText(),
                Integer.parseInt(courseNumber.getText()),
                courseSubject.getText(), 
                professorName.getText(), 
                Semester.valueOf(semester.getText().toUpperCase()),
                Integer.parseInt(year.getText()));
        getDatabase().add(s);
        getDatabase().writeSyllabye();
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
