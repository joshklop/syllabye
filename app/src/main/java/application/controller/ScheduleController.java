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
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;


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
            int i = 0;
            Color[] colors = {Color.RED, Color.BLUE, Color.BLUEVIOLET, Color.DARKCYAN, Color.DARKORANGE, Color.INDIANRED};
            for (Syllabus s : syllabyeBySemester.get(newValue)) {
                for (DayOfWeek d : s.getLectureDayTimes().keySet()) {
                    switch (d) {
                        case MONDAY:
                            makeLabel(mondayBox, s, d, colors[i]);
                            break;
                        case TUESDAY:
                            makeLabel(tuesdayBox, s, d, colors[i]);
                            break;
                        case WEDNESDAY:
                            makeLabel(wednesdayBox, s, d, colors[i]);
                            break;
                        case THURSDAY:
                            makeLabel(thursdayBox, s, d, colors[i]);
                            break;
                        case FRIDAY:
                            makeLabel(fridayBox, s, d, colors[i]);
                            break;
                    }
                }
                i++;
            }
            i = 0;
        });
        
    }

    private void makeLabel(VBox box, Syllabus s, DayOfWeek d, Color c) {
    	String courseInfo = s.getCourseSubject() + " " + s.getCourseNumber() + " " + s.getCourseName();
    	box.setSpacing(10);
    	
        Label l = new Label(courseInfo);
        l.setTextFill(c);
        l.setWrapText(true);
        l.setContentDisplay(ContentDisplay.CENTER);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setAlignment(Pos.CENTER);
        l.setFont(Font.font(14));
        l.setPrefWidth(box.getWidth());
       
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
