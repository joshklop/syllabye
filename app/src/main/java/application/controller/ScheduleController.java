package application.controller;

import application.model.Database;
import application.model.Syllabus;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import java.util.HashMap;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import java.io.IOException;
import java.util.ArrayList;

public class ScheduleController implements Initializable {
    @FXML
    private GridPane agenda;
    private static Database db;
    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private Button homeButton;
    private HashMap<String, ArrayList<Syllabus>> syllabyeBySemester = new HashMap<String, ArrayList<Syllabus>>();

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        String semester;
        for (Syllabus s : db.getSyllabye().values()) {
            semester = s.getSemester().toString();
            syllabyeBySemester.putIfAbsent(semester + " " + s.getYear(), new ArrayList<Syllabus>());
            syllabyeBySemester.get(semester + " " + s.getYear()).add(s);
        }
        // TODO sort semesters by month, year
        semesterComboBox.getItems().addAll(syllabyeBySemester.keySet());
    }

    public void selectSemester(ActionEvent e) {
        syllabyeBySemester.get(((ComboBox<String>)e.getSource()).getValue());
        // TODO Remove all labels
        // Add labels
    }

    public void goToSelectionPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectionPage.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void setDatabase(Database db) {
        ScheduleController.db = db;
    }
}
