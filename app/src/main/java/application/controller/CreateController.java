package application.controller;

import application.model.Syllabus;
import application.model.Semester;
import application.model.Database;
import application.model.LectureTime;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.scene.control.CheckBox;
import java.util.ArrayList;
import javafx.concurrent.Task;
import java.lang.Thread;

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
    @FXML
    private CheckBox recitation;

    // Lecture Times
    @FXML
    private ComboBox<LocalTime> mondayStart;
    @FXML
    private ComboBox<LocalTime> mondayEnd;
    @FXML
    private ComboBox<LocalTime> tuesdayStart;
    @FXML
    private ComboBox<LocalTime> tuesdayEnd;
    @FXML
    private ComboBox<LocalTime> wednesdayStart;
    @FXML
    private ComboBox<LocalTime> wednesdayEnd;
    @FXML
    private ComboBox<LocalTime> thursdayStart;
    @FXML
    private ComboBox<LocalTime> thursdayEnd;
    @FXML
    private ComboBox<LocalTime> fridayStart;
    @FXML
    private ComboBox<LocalTime> fridayEnd;

    private int minuteIncrement = 15;
    private ArrayList<LectureTimeComboBox> timeBoxes = new ArrayList<>();
    private static Database db;

    // TODO make lectureTimeCombo editable
    public class LectureTimeComboBox {
        ComboBox<LocalTime> startBox;
        ComboBox<LocalTime> endBox;
        DayOfWeek day;

        public LectureTimeComboBox(ComboBox<LocalTime> startBox, ComboBox<LocalTime> endBox, DayOfWeek day) {
            this.setStart(startBox);
            this.setEnd(endBox);
            this.setDay(day);
        }

        public ComboBox<LocalTime> getStart() {
            return startBox;
        }

        public void setStart(ComboBox<LocalTime> startBox) {
            this.startBox = startBox;
        }

        public ComboBox<LocalTime> getEnd() {
            return endBox;
        }

        public void setEnd(ComboBox<LocalTime> endBox) {
            this.endBox = endBox;
        }

        public DayOfWeek getDay() {
            return day;
        }

        public void setDay(DayOfWeek day) {
            this.day = day;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // Note: the order of these additions is important. We assume they are in chronological
        // order and that the start/end boxes alternate.
        timeBoxes.add(new LectureTimeComboBox(mondayStart, mondayEnd, DayOfWeek.MONDAY));
        timeBoxes.add(new LectureTimeComboBox(tuesdayStart, tuesdayEnd, DayOfWeek.TUESDAY));
        timeBoxes.add(new LectureTimeComboBox(wednesdayStart, wednesdayEnd, DayOfWeek.WEDNESDAY));
        timeBoxes.add(new LectureTimeComboBox(thursdayStart, thursdayEnd, DayOfWeek.THURSDAY));
        timeBoxes.add(new LectureTimeComboBox(fridayStart, fridayEnd, DayOfWeek.FRIDAY));

        for (Semester s : Semester.values()) {
            semester.getItems().add(s.toString());
        }
        semester.setValue("Spring");

        // TODO change extraCredit to checkbox
        extraCredit.getItems().add("No");
        extraCredit.getItems().add("Yes");
        extraCredit.getSelectionModel().selectFirst();

        ArrayList<LocalTime> times = new ArrayList<LocalTime>(24 * 60 / minuteIncrement);
        Task<ArrayList<LocalTime>> task = new Task<>() {
            @Override protected ArrayList<LocalTime> call() {
                LocalTime time = LocalTime.of(0, 0);
                do {
                    times.add(time); 
                    time = time.plusMinutes(minuteIncrement);
                } while (time.compareTo(LocalTime.of(0, 0)) > 0);
                return times;
            }

            @Override protected void succeeded() {
                super.succeeded();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
                LocalTimeStringConverter converter = new LocalTimeStringConverter(formatter, formatter);
                // TODO there should be a "default" value that allows users to cancel a selection...
                for (LectureTimeComboBox box : timeBoxes) {
                    // Add times to the boxes
                    box.getStart().setConverter(converter);
                    box.getStart().getItems().addAll(times);
                    box.getEnd().setConverter(converter);
                    box.getEnd().getItems().addAll(times);
                    // Filter available times when a time is selected
                    box.getStart().setOnAction(e -> {
                        // Ensure at least one of the two boxes does not have a value
                        if (box.getStart().getSelectionModel().isEmpty() || box.getEnd().getSelectionModel().isEmpty()) {
                            // TODO there is a more efficient way to filter them since we know they are already sorted
                            box.getEnd().setItems(box.getEnd().getItems().filtered(time -> time.isAfter(box.getStart().getValue())));
                        }
                    });
                    box.getEnd().setOnAction(e -> {
                        if (box.getStart().getSelectionModel().isEmpty() || box.getEnd().getSelectionModel().isEmpty()) {
                            box.getStart().setItems(box.getStart().getItems().filtered(time -> time.isBefore(box.getEnd().getValue())));
                        }
                    });
                }
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

    @FXML
    public void onSave(ActionEvent event) throws IOException {
        // Get lecture times into HashMap
        HashMap<DayOfWeek,LectureTime> lectureDayTimes = new HashMap<DayOfWeek,LectureTime>();
        for (LectureTimeComboBox box : timeBoxes) {
            if (box.getStart().getValue() != null && box.getEnd().getValue() != null) {
                lectureDayTimes.put(box.getDay(), new LectureTime(box.getStart().getValue(), box.getEnd().getValue()));
            }
        }

        String subject = courseSubject.getText().toUpperCase().strip();
        String number = courseNumber.getText().strip();
        String sem = semester.getValue().strip().toUpperCase();
        String yr = year.getText().strip();

        // TODO add converters and formatters to make this error checking cleaner
        if (subject.isBlank() || number.isBlank() || yr.isBlank()) {
            warning.setText("Fill out all fields marked with '*'");
        } else {
            Syllabus s = new Syllabus(courseName.getText().strip(), Integer.parseInt(number),
                subject, professorName.getText().strip(), Semester.valueOf(sem),
                Integer.parseInt(yr), location.getText().strip(),
                professorEmail.getText().strip(), extraCredit.getValue().equals("Yes"), lectureDayTimes,
                recitation.isSelected());
            if (db.getSyllabye().containsKey(Database.computeKey(s))) {
                warning.setText("A course or recitation with that subject, number, semester, and year already exists.");
            } else {
                try {
                    // Save the syllabus data
                    warning.setText("");
                    db.add(s);
                    db.writeSyllabye();
                    // Go back to SelectionPage scene
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (NumberFormatException e) {
                    warning.setText("'Course Number' and 'Year' must be numbers");
                }
            }
        }
    }

    @FXML
    public void goHome(ActionEvent event) throws IOException {
        // Note: nothing will save when the user presses the home button
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void setDatabase(Database db) {
        CreateController.db = db;
    }
}
