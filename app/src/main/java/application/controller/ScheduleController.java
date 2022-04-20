package application.controller;

import application.model.Database;
import application.model.Syllabus;
import javafx.fxml.FXML;
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
import java.time.DayOfWeek;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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
        semesterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue == oldValue) {
                return;
            }
            // Remove existing labels
            // Add new labels
            for (Syllabus s : syllabyeBySemester.get(newValue)) {
                for (DayOfWeek d : s.getLectureDayTimes().keySet()) {
                    switch (d) {
                        case MONDAY:
                            agenda.add(makeLabel(s, d), 0, nextEmptyCell(0));
                            break;
                        case TUESDAY:
                            agenda.add(makeLabel(s, d), 1, nextEmptyCell(1));
                            break;
                        case WEDNESDAY:
                            agenda.add(makeLabel(s, d), 2, nextEmptyCell(2));
                            break;
                        case THURSDAY:
                            agenda.add(makeLabel(s, d), 3, nextEmptyCell(3));
                            break;
                        case FRIDAY:
                            agenda.add(makeLabel(s, d), 4, nextEmptyCell(4));
                            break;
                    }
                }
            }
        });
    }

    private int nextEmptyCell(int column) {
        for (int i = column; i < agenda.getColumnCount() * agenda.getRowCount(); i += agenda.getColumnCount()) {
            System.out.println("hello");
            System.out.println(agenda.getChildren().get(i));
            if (agenda.getChildren().get(i).equals(null)) {
                return i / agenda.getColumnCount();
            }
        }
        // This should overwrite the last item in the column
        return column * agenda.getRowCount();
    }

    private Label makeLabel(Syllabus s, DayOfWeek d) {
        return new Label(s.getCourseSubject() + " " + s.getCourseNumber() + "\n" + s.getLectureDayTimes().get(d));
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
