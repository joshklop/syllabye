package application.controller;

import application.model.SemesterYear;
import application.model.Database;
import application.model.Syllabus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import java.util.HashMap;
import java.util.TreeMap;
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
    private ComboBox<SemesterYear> semesterComboBox;
    @FXML
    private Button homeButton;

    private TreeMap<SemesterYear, ArrayList<Syllabus>> syllabyeBySemester = new TreeMap<>();

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        HashMap<DayOfWeek, VBox> boxes = new HashMap<>();
        boxes.put(DayOfWeek.MONDAY, mondayBox);
        boxes.put(DayOfWeek.TUESDAY, tuesdayBox);
        boxes.put(DayOfWeek.WEDNESDAY, wednesdayBox);
        boxes.put(DayOfWeek.THURSDAY, thursdayBox);
        boxes.put(DayOfWeek.FRIDAY, fridayBox);
        boxes.forEach((day, box) -> box.setSpacing(10));

        // Map semesters to syllabye
        Syllabus[] syllabye = db.getSyllabye().values().toArray(new Syllabus[0]);
        for (int i = 0; i < syllabye.length; ++i) {
            SemesterYear key = new SemesterYear(syllabye[i].getSemester(), syllabye[i].getYear());
            syllabyeBySemester.putIfAbsent(key, new ArrayList<Syllabus>());
            syllabyeBySemester.get(key).add(syllabye[i]);
        }

        // Add semesters (in sorted order) to ComboBox
        semesterComboBox.getItems().addAll(syllabyeBySemester.keySet());

        // Map colors to syllabye
        Color[] availableColors = { 
            Color.RED, Color.BLUE, Color.BLUEVIOLET, Color.DARKCYAN, Color.DARKORANGE,
            Color.INDIANRED, Color.BLACK, Color.DEEPPINK, Color.GRAY, Color.GREEN, Color.BROWN, 
        };
        HashMap<String, Color> colors = new HashMap<>();
        for (int i = 0; i < syllabye.length; ++i) {
            colors.put(Database.computeKey(syllabye[i]), availableColors[i % availableColors.length]);
        }

        // Add class labels to agenda when semester is selected
        semesterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals(oldValue)) {
                return;
            }

            // Remove existing labels
            boxes.forEach((day, box) -> {
                if (box.getChildren().size() > 1) {
                    box.getChildren().remove(1, box.getChildren().size());
                }
            });

            // Organize syllabye by day
            HashMap<DayOfWeek, ArrayList<Syllabus>> syllabyeByDay = new HashMap<DayOfWeek,ArrayList<Syllabus>>();
            for (Syllabus s : syllabyeBySemester.get(newValue)) {
                // Add new labels
                for (DayOfWeek d : s.getLectureDayTimes().keySet()) {
                    syllabyeByDay.putIfAbsent(d, new ArrayList<Syllabus>());
                    syllabyeByDay.get(d).add(s);
                }
            }

            syllabyeByDay.forEach((day, aList) -> {
                // Sort syllabye within each day
                aList.sort((s1, s2) -> {
                    return s1.getLectureDayTimes()
                    .get(day)
                    .getStart()
                    .compareTo(s2.getLectureDayTimes().get(day).getStart());
                });
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
                // Add Labels
                for (int i = 0; i < aList.size(); ++i) {
                    Syllabus s = aList.get(i);
                    String startTime = s.getLectureDayTimes().get(day).getStart().format(formatter);
                    String endTime = s.getLectureDayTimes().get(day).getEnd().format(formatter);
                    String text; 
                    if (s.isRecitation()) {
                        text = s.getCourseSubject() + " " + s.getCourseNumber() + " " + s.getCourseName() + " Recitation\n" + startTime + "-" + endTime;
                    } else {
                        text = s.getCourseSubject() + " " + s.getCourseNumber() + " " + s.getCourseName() + "\n" + startTime + "-" + endTime;
                    }
                    Label l = new Label(text);
                    l.setTextFill(colors.get(Database.computeKey(s)));
                    l.setWrapText(true);
                    l.setContentDisplay(ContentDisplay.CENTER);
                    l.setTextAlignment(TextAlignment.CENTER);
                    l.setAlignment(Pos.CENTER);
                    l.setFont(Font.font(13));
                    l.setPrefWidth(boxes.get(day).getWidth());
                    boxes.get(day).getChildren().add(l);
                }
            });
        });
    }

    public void goToSelectionPage(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void setDatabase(Database db) {
        ScheduleController.db = db;
    }
}
