package application.controller;

import application.model.Syllabus;
import application.model.Semester;
import application.model.Database;
import application.model.LectureTime;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

/**
 * The CreateController implements the Initializable interface and controls the Create page.
 * Users can manually input their information or upload a text file. 
 * Then it creates a syllbus with the given information.
 */

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
    private Label fileWarning;
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
    
     /**
     * The initialize method initializes all of the GUI elements in the Create page.
     * It adds the possible start/end times for lectures and recitations, adds the possible semesters, 
     * and so on.
     * 
     * @param url
     * @param bundle
     */
    
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
    
    /**
     * The onSave method is in charge of saving the information the user has provided and
     * creates a Syllabus object with it. It also error-checks if a course already exists and if 
     * they have not provided the required values.
     * 
     * @param event
     * @throws IOException
     */

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
    
    /**
     * The goHome method switches the page to the Selection page whenever the user
     * clicks on the home button.
     * 
     * @param event
     * @throws IOException
     */

    @FXML
    public void goHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    /**
     * The uploadFile method is triggered when the user clicks on the "Upload File" button.
     * It lets the user upload a text file from their computer.
     * 
     * @param event
     * @throws IOException
     */

    public void uploadFile(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text files", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

    if (file != null) {
            Scanner scanner = new Scanner(file);
            ArrayList<String> l = new ArrayList<String>();
            //Reads the file by line
            while(scanner.hasNextLine()) {
                //parse string
                l.add(scanner.nextLine());
            }
            scanner.close();

            String[] arr = new String[l.size()];
            //Removes the prompt for every line. Ex: Course Name: is removed
            for(int i = 0; i < l.size(); i++) {
                String line = l.get(i).toString().trim().substring(l.get(i).toString().lastIndexOf(": ") + 1);
                arr[i] = line;
            }
            //setText is called
            setText(arr);

        } else {
            fileWarning.setText("Error opening file.");
        }
    }
    
    /**
     * The method setText sets the text and values for all of the textfields / comboboxes in the
     * Create page. This method is triggered after the user successfully uploads a file.
     * @param arr
     */

    public void setText(String[] arr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");

        year.setText(arr[1]);
        courseSubject.setText(arr[2]);
        courseNumber.setText(arr[3]);
        courseName.setText(arr[4]);
        location.setText(arr[5]);
        professorName.setText(arr[6]);
        professorEmail.setText(arr[7]);;
        extraCredit.setValue(arr[8].trim());

        String[] lTime = arr[9].split("[,]",0);

    for(int i = 0; i < lTime.length; i++) {
            String time = lTime[i].substring(3, lTime[i].length());
            String[] times = time.split(" - ");
            String start = times[0].trim();
            String end = times[1].trim();

            LocalTime startLocalTime = LocalTime.parse(start, formatter);
            LocalTime endLocalTime = LocalTime.parse(end,formatter);

            String day = lTime[i].substring(1,3).trim();

        switch(day) {
                case "M":
                    mondayStart.setValue(startLocalTime);
                    mondayEnd.setValue(endLocalTime);
                    break;
                case "T":
                    tuesdayStart.setValue(startLocalTime);
                    tuesdayEnd.setValue(endLocalTime);
                    break;
                case "W":
                    wednesdayStart.setValue(startLocalTime);
                    wednesdayEnd.setValue(endLocalTime);
                    break;
                case "TR":
                    thursdayStart.setValue(startLocalTime);
                    thursdayEnd.setValue(endLocalTime);
                    break;
                case "F":
                    fridayStart.setValue(startLocalTime);
                    fridayEnd.setValue(endLocalTime);
                break;
            }
        }
        arr[10] = arr[10].trim();

    	if(arr[10].equals("Yes")) {
            recitation.setSelected(true);
        }

    }
    
    /**
     * Transfers the database data to the CreateController.
     * @param db
     */
    
    public static void setDatabase(Database db) {
        CreateController.db = db;
    }
}
