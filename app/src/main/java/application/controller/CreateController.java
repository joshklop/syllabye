package application.controller;

import application.model.Syllabus;
import application.model.Semester;
import application.model.Database;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateController implements Initializable {
    // Syllabus fields
    @FXML
    private TextField courseSubject;
    @FXML
    private TextField courseName;
    @FXML
    private TextField courseNumber;
    @FXML
    private TextField professorName;
    @FXML
    private TextField year;
    @FXML
    private ChoiceBox<String> semester;
    @FXML
    private TextField location;
    @FXML
    private TextField professorEmail;
    @FXML
    private ChoiceBox<String> extraCredit;
    @FXML
    private Label warning;

    private static Database db;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        String sem;
        // There are only a few semesters so the loop should not hang the application
        for (Semester s : Semester.values()) {
            sem = s.toString();
            semester.getItems().add(sem.substring(0, 1).toUpperCase() + sem.substring(1).toLowerCase());
        }
        semester.setValue("Spring");
        extraCredit.setValue("Yes");
        extraCredit.getItems().add("No");
    }

    @FXML
    public void onSave(ActionEvent event) throws IOException {
        String subject = courseSubject.getText().strip();
        String number = courseNumber.getText().strip();
        String sem = semester.getValue().strip().toUpperCase();
        String yr = year.getText().strip();
        if (subject.isBlank() || number.isBlank() || yr.isBlank()) {
            warning.setText("Fill out all fields marked with '*'");
        } else if (getDatabase().getSyllabye().containsKey(subject + number + sem + yr)) {
            warning.setText("A syllabus with that subject, number, semester, and year already exists.");
        } else {
            try {
                // Save the syllabus data
                Syllabus s = new Syllabus(courseName.getText().strip(), Integer.parseInt(number),
                        subject, professorName.getText().strip(), Semester.valueOf(sem),
                        Integer.parseInt(yr), location.getText().strip(),
                        professorEmail.getText().strip(), extraCredit.getValue().equals("Yes"));
                warning.setText("");
                getDatabase().add(s);
                getDatabase().writeSyllabye();
                // Go back to SelectionPage scene
                // TODO this "switchScene" logic is repeated in many places
                // Should we make all controller classes inherit from an abstract "Controller" class?
                Parent root = FXMLLoader.load(getClass().getResource(File.separator + "fxml" + File.separator + "SelectionPage.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (NumberFormatException e) {
                warning.setText("'Course Number' and 'Year' must be numbers");
            }
        }
    }

    public static Database getDatabase() {
        return db;
    }

    public static void setDatabase(Database db) {
        CreateController.db = db;
    }
}
