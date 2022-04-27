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
import java.time.format.DateTimeFormatter;


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
            int i = 0;
            Color[] colors = {Color.RED, Color.BLUE, Color.BLUEVIOLET, Color.DARKCYAN, Color.DARKORANGE, Color.INDIANRED};
            for (Syllabus s : syllabyeBySemester.get(newValue)) {
                // TODO sort by day and then start time
                // Add new labels
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
                
                for (DayOfWeek d : s.getRecitationTimes().keySet()) {
            	   switch (d) {
                   case MONDAY:
                       makeRecitationLabel(mondayBox, s, d, colors[i]);
                       break;
                   case TUESDAY:
                       makeRecitationLabel(tuesdayBox, s, d, colors[i]);
                       break;
                   case WEDNESDAY:
                       makeRecitationLabel(wednesdayBox, s, d, colors[i]);
                       break;
                   case THURSDAY:
                       makeRecitationLabel(thursdayBox, s, d, colors[i]);
                       break;
                   case FRIDAY:
                       makeRecitationLabel(fridayBox, s, d, colors[i]);
                       break;
               }
               }
               i++;
            }
            i = 0;
        });
        
     // Remove existing labels
        removeLabels(mondayBox);
        removeLabels(tuesdayBox);
        removeLabels(wednesdayBox);
        removeLabels(thursdayBox);
        removeLabels(fridayBox);

    }

    private void removeLabels(VBox box) {
        if (box.getChildren().size() > 1) {
            box.getChildren().remove(1, box.getChildren().size());
        }
    }

    private void makeLabel(VBox box, Syllabus s, DayOfWeek d, Color c) {
        box.setSpacing(10);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        String startTime = s.getLectureDayTimes().get(d).getStart().format(formatter);
        String endTime = s.getLectureDayTimes().get(d).getEnd().format(formatter);
        String text = s.getCourseSubject() + " " + s.getCourseNumber() + " " + s.getCourseName() + "\n" + startTime + "-" + endTime;

        Label l = new Label(text);
        l.setTextFill(c);
        l.setWrapText(true);
        l.setContentDisplay(ContentDisplay.CENTER);
        l.setTextAlignment(TextAlignment.CENTER);
        l.setAlignment(Pos.CENTER);
        l.setFont(Font.font(13));
        l.setPrefWidth(box.getWidth());
        
        box.getChildren().add(l);
           
    }
    
    private void makeRecitationLabel(VBox box, Syllabus s, DayOfWeek d, Color c) {
    	box.setSpacing(10);
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        String rStart = s.getRecitationTimes().get(d).getRecitationStart().format(formatter);
        String rEnd = s.getRecitationTimes().get(d).getRecitationEnd().format(formatter);
        String recitations = s.getCourseSubject() + " " + s.getCourseNumber() + " " + s.getCourseName() + "\nRecitation\n" + rStart + "-" + rEnd;
        	
        Label r = new Label(recitations);
        r.setTextFill(c);
        r.setWrapText(true);
        r.setContentDisplay(ContentDisplay.CENTER);
        r.setTextAlignment(TextAlignment.CENTER);
        r.setAlignment(Pos.CENTER);
        r.setFont(Font.font(13));
        r.setPrefWidth(box.getWidth());
            
        box.getChildren().add(r);
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
