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
import javafx.scene.layout.VBox;

public class ScheduleController implements Initializable {
    @FXML
    private VBox mondayBox;
    @FXML
    private VBox tuesdayBox;
    @FXML
    private VBox wednesdayBox;
    @FXML
    private VBox thursdayBox;
    @FXML
    private VBox fridayBox;
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
                            makeLabel(mondayBox, s, d);
                            break;
                        case TUESDAY:
                            makeLabel(tuesdayBox, s, d);
                            break;
                        case WEDNESDAY:
                            makeLabel(wednesdayBox, s, d);
                            break;
                        case THURSDAY:
                            makeLabel(thursdayBox, s, d);
                            break;
                        case FRIDAY:
                            makeLabel(fridayBox, s, d);
                            break;
                    }
                }
            }
        });
    }

    private void makeLabel(VBox box, Syllabus s, DayOfWeek d) {
        Label l = new Label(s.getCourseSubject() + " " + s.getCourseNumber());
        l.setPrefWidth(box.getWidth());
        l.setMinWidth(box.getWidth());
        l.setMaxWidth(box.getWidth());
        box.getChildren().add(l);
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
