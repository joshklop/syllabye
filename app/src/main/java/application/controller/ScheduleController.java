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
        // Add class labels to agenda when semester is selected
        semesterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue == oldValue) {
                return;
            }
            // Remove existing labels
            removeLabels(mondayBox);
            removeLabels(tuesdayBox);
            removeLabels(wednesdayBox);
            removeLabels(thursdayBox);
            removeLabels(fridayBox);
            ArrayList<Syllabus> monday = new ArrayList<Syllabus>();
            ArrayList<Syllabus> tuesday = new ArrayList<Syllabus>();
            ArrayList<Syllabus> wednesday = new ArrayList<Syllabus>();
            ArrayList<Syllabus> thursday = new ArrayList<Syllabus>();
            ArrayList<Syllabus> friday = new ArrayList<Syllabus>();
            for (Syllabus s : syllabyeBySemester.get(newValue)) {
                // Add new labels
                for (DayOfWeek d : s.getLectureDayTimes().keySet()) {
                    switch (d) {
                        case MONDAY:
                            monday.add(s);
                            break;
                        case TUESDAY:
                            tuesday.add(s);
                            break;
                        case WEDNESDAY:
                            wednesday.add(s);
                            break;
                        case THURSDAY:
                            thursday.add(s);
                            break;
                        case FRIDAY:
                            friday.add(s);
                            break;
                    }
                }
            }

            makeLabels(mondayBox, monday, DayOfWeek.MONDAY);
            makeLabels(tuesdayBox, tuesday, DayOfWeek.TUESDAY);
            makeLabels(wednesdayBox, wednesday, DayOfWeek.WEDNESDAY);
            makeLabels(thursdayBox, thursday, DayOfWeek.THURSDAY);
            makeLabels(fridayBox, friday, DayOfWeek.FRIDAY);
        });

    }

    private void removeLabels(VBox box) {
        if (box.getChildren().size() > 1) {
            box.getChildren().remove(1, box.getChildren().size());
        }
    }

    private void makeLabels(VBox box, ArrayList<Syllabus> alist, DayOfWeek day) {
        // Sort syllabye by time
        alist.sort((x, y) -> {
            return x.getLectureDayTimes()
            .get(day)
            .getStart()
            .compareTo(y.getLectureDayTimes().get(day).getStart());
        });

        box.setSpacing(10);

        Color[] colors = { Color.RED, Color.BLUE, Color.BLUEVIOLET, Color.DARKCYAN, Color.DARKORANGE, Color.INDIANRED };
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        for (int i = 0; i < alist.size(); ++i) {
            Syllabus s = alist.get(i);
            String startTime = s.getLectureDayTimes().get(day).getStart().format(formatter);
            String endTime = s.getLectureDayTimes().get(day).getEnd().format(formatter);
            String text = s.getCourseSubject() + " " + s.getCourseNumber() + " " + s.getCourseName() + "\n" + startTime + "-" + endTime;

            Label l = new Label(text);
            l.setTextFill(colors[i % colors.length]);
            l.setWrapText(true);
            l.setContentDisplay(ContentDisplay.CENTER);
            l.setTextAlignment(TextAlignment.CENTER);
            l.setAlignment(Pos.CENTER);
            l.setFont(Font.font(14));
            l.setPrefWidth(box.getWidth());

            box.getChildren().add(l);
        }
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
