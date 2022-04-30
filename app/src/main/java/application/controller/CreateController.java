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
import java.util.Arrays;
import java.util.ArrayList;
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
    @FXMl
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

    private static Database db;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        // TODO store as semester
        String sem;
        // There are only a few semesters so the loop should not hang the application
        for (Semester s : Semester.values()) {
            sem = s.toString();
            semester.getItems().add(sem.substring(0, 1).toUpperCase() + sem.substring(1).toLowerCase());
        }
        semester.setValue("Spring");

        extraCredit.getItems().add("No");
        extraCredit.getItems().add("Yes");
        extraCredit.getSelectionModel().selectFirst();

        String times[] = {
            "12:00AM", "12:05AM", "12:10AM", "12:15AM", "12:20AM", "12:25AM", "12:30AM", "12:35AM",
            "12:40AM", "12:45AM", "12:50AM", "12:55AM", "1:00AM", "1:05AM", "1:10AM", "1:15AM",
            "1:20AM", "1:25AM", "1:30AM", "1:35AM", "1:40AM", "1:45AM", "1:50AM", "1:55AM",
            "2:00AM", "2:05AM", "2:10AM", "2:15AM", "2:20AM", "2:25AM", "2:30AM", "2:35AM",
            "2:40AM", "2:45AM", "2:50AM", "2:55AM", "3:00AM", "3:05AM", "3:10AM", "3:15AM",
            "3:20AM", "3:25AM", "3:30AM", "3:35AM", "3:40AM", "3:45AM", "3:50AM", "3:55AM",
            "4:00AM", "4:05AM", "4:10AM", "4:15AM", "4:20AM", "4:25AM", "4:30AM", "4:35AM",
            "4:40AM", "4:45AM", "4:50AM", "4:55AM", "5:00AM", "5:05AM", "5:10AM", "5:15AM",
            "5:20AM", "5:25AM", "5:30AM", "5:35AM", "5:40AM", "5:45AM", "5:50AM", "5:55AM",
            "6:00AM", "6:05AM", "6:10AM", "6:15AM", "6:20AM", "6:25AM", "6:30AM", "6:35AM",
            "6:40AM", "6:45AM", "6:50AM", "6:55AM", "7:00AM", "7:05AM", "7:10AM", "7:15AM",
            "7:20AM", "7:25AM", "7:30AM", "7:35AM", "7:40AM", "7:45AM", "7:50AM", "7:55AM",
            "8:00AM", "8:05AM", "8:10AM", "8:15AM", "8:20AM", "8:25AM", "8:30AM", "8:35AM",
            "8:40AM", "8:45AM", "8:50AM", "8:55AM", "9:00AM", "9:05AM", "9:10AM", "9:15AM",
            "9:20AM", "9:25AM", "9:30AM", "9:35AM", "9:40AM", "9:45AM", "9:50AM", "9:55AM",
            "10:00AM", "10:05AM", "10:10AM", "10:15AM", "10:20AM", "10:25AM", "10:30AM", "10:35AM",
            "10:40AM", "10:45AM", "10:50AM", "10:55AM", "11:00AM", "11:05AM", "11:10AM", "11:15AM",
            "11:20AM", "11:25AM", "11:30AM", "11:35AM", "11:40AM", "11:45AM", "11:50AM", "11:55AM",
            "12:00PM", "12:05PM", "12:10PM", "12:15PM", "12:20PM", "12:25PM", "12:30PM", "12:35PM",
            "12:40PM", "12:45PM", "12:50PM", "12:55PM", "1:00PM", "1:05PM", "1:10PM", "1:15PM",
            "1:20PM", "1:25PM", "1:30PM", "1:35PM", "1:40PM", "1:45PM", "1:50PM", "1:55PM",
            "2:00PM", "2:05PM", "2:10PM", "2:15PM", "2:20PM", "2:25PM", "2:30PM", "2:35PM",
            "2:40PM", "2:45PM", "2:50PM", "2:55PM", "3:00PM", "3:05PM", "3:10PM", "3:15PM",
            "3:20PM", "3:25PM", "3:30PM", "3:35PM", "3:40PM", "3:45PM", "3:50PM", "3:55PM",
            "4:00PM", "4:05PM", "4:10PM", "4:15PM", "4:20PM", "4:25PM", "4:30PM", "4:35PM",
            "4:40PM", "4:45PM", "4:50PM", "4:55PM", "5:00PM", "5:05PM", "5:10PM", "5:15PM",
            "5:20PM", "5:25PM", "5:30PM", "5:35PM", "5:40PM", "5:45PM", "5:50PM", "5:55PM",
            "6:00PM", "6:05PM", "6:10PM", "6:15PM", "6:20PM", "6:25PM", "6:30PM", "6:35PM",
            "6:40PM", "6:45PM", "6:50PM", "6:55PM", "7:00PM", "7:05PM", "7:10PM", "7:15PM",
            "7:20PM", "7:25PM", "7:30PM", "7:35PM", "7:40PM", "7:45PM", "7:50PM", "7:55PM",
            "8:00PM", "8:05PM", "8:10PM", "8:15PM", "8:20PM", "8:25PM", "8:30PM", "8:35PM",
            "8:40PM", "8:45PM", "8:50PM", "8:55PM", "9:00PM", "9:05PM", "9:10PM", "9:15PM",
            "9:20PM", "9:25PM", "9:30PM", "9:35PM", "9:40PM", "9:45PM", "9:50PM", "9:55PM",
            "10:00PM", "10:05PM", "10:10PM", "10:15PM", "10:20PM", "10:25PM", "10:30PM", "10:35PM",
            "10:40PM", "10:45PM", "10:50PM", "10:55PM", "11:00PM", "11:05PM", "11:10PM", "11:15PM",
            "11:20PM", "11:25PM", "11:30PM", "11:35PM", "11:40PM", "11:45PM", "11:50PM", "11:55PM",
        };
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
        LocalTime[] localTimes = Arrays.stream(times)
            .map(t -> LocalTime.parse(t, formatter))
            .toArray(LocalTime[]::new);
        LocalTimeStringConverter converter = new LocalTimeStringConverter(formatter, formatter);
        // and times instead. Didn't have time for that on the first try
        // TODO there should be a "default" value that allows users to cancel a selection...
        //Lecture Times
        mondayStart.setConverter(converter);
        mondayStart.getItems().addAll(localTimes);
        mondayEnd.setConverter(converter);
        mondayEnd.getItems().addAll(localTimes);
        tuesdayStart.setConverter(converter);
        tuesdayStart.getItems().addAll(localTimes);
        tuesdayEnd.setConverter(converter);
        tuesdayEnd.getItems().addAll(localTimes);
        wednesdayStart.setConverter(converter);
        wednesdayStart.getItems().addAll(localTimes);
        wednesdayEnd.setConverter(converter);
        wednesdayEnd.getItems().addAll(localTimes);
        thursdayStart.setConverter(converter);
        thursdayStart.getItems().addAll(localTimes);
        thursdayEnd.setConverter(converter);
        thursdayEnd.getItems().addAll(localTimes);
        fridayStart.setConverter(converter);
        fridayStart.getItems().addAll(localTimes);
        fridayEnd.setConverter(converter);
        fridayEnd.getItems().addAll(localTimes);
    }

    @FXML
    public void onSave(ActionEvent event) throws IOException {
        // Get lecture times into HashMap
        HashMap<DayOfWeek,LectureTime> lectureDayTimes = new HashMap<DayOfWeek,LectureTime>();
        if (mondayStart.getValue() != null && mondayEnd.getValue() != null) {
            lectureDayTimes.put(DayOfWeek.MONDAY, new LectureTime(mondayStart.getValue(), mondayEnd.getValue()));
        }
        if (tuesdayStart.getValue() != null && tuesdayEnd.getValue() != null) {
            lectureDayTimes.put(DayOfWeek.TUESDAY, new LectureTime(tuesdayStart.getValue(), tuesdayEnd.getValue()));
        }
        if (wednesdayStart.getValue() != null && wednesdayEnd.getValue() != null) {
            lectureDayTimes.put(DayOfWeek.WEDNESDAY, new LectureTime(wednesdayStart.getValue(), wednesdayEnd.getValue()));
        }
        if (thursdayStart.getValue() != null && thursdayEnd.getValue() != null) {
            lectureDayTimes.put(DayOfWeek.THURSDAY, new LectureTime(thursdayStart.getValue(), thursdayEnd.getValue()));
        }
        if (fridayStart.getValue() != null && fridayEnd.getValue() != null) {
            lectureDayTimes.put(DayOfWeek.FRIDAY, new LectureTime(fridayStart.getValue(), fridayEnd.getValue()));
        }

        String subject = courseSubject.getText().toUpperCase().strip();
        String number = courseNumber.getText().strip();
        String sem = semester.getValue().strip().toUpperCase();
        String yr = year.getText().strip();

        Syllabus s = new Syllabus(courseName.getText().strip(), Integer.parseInt(number),
            subject, professorName.getText().strip(), Semester.valueOf(sem),
            Integer.parseInt(yr), location.getText().strip(),
            professorEmail.getText().strip(), extraCredit.getValue().equals("Yes"), lectureDayTimes, recitation.isSelected());
        if (subject.isBlank() || number.isBlank() || yr.isBlank()) {
            warning.setText("Fill out all fields marked with '*'");
        } else if (db.getSyllabye().containsKey(Database.computeKey(s))) {
            warning.setText("A course or recitation with that subject, number, semester, and year already exists.");
        } else {
            try {
                // Save the syllabus data
                warning.setText("");
                db.add(s);
                db.writeSyllabye();
                // Go back to SelectionPage scene
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectionPage.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (NumberFormatException e) {
                warning.setText("'Course Number' and 'Year' must be numbers");
            }
        }
    }

    @FXML
    public void goHome(ActionEvent event) throws IOException {
        // Note: nothing will save when the user presses the home button
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectionPage.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void filterTimes(ActionEvent event) {
        if (mondayStart.getValue() != null) {
            mondayEnd.setItems(mondayEnd.getItems().filtered(time -> time.isAfter(mondayStart.getValue())));
        }
        if (tuesdayStart.getValue() != null) {
            tuesdayEnd.setItems(tuesdayEnd.getItems().filtered(time -> time.isAfter(tuesdayStart.getValue())));
        }
        if (wednesdayStart.getValue() != null) {
            wednesdayEnd.setItems(wednesdayEnd.getItems().filtered(time -> time.isAfter(wednesdayStart.getValue())));
        }
        if (thursdayStart.getValue() != null) {
            thursdayEnd.setItems(thursdayEnd.getItems().filtered(time -> time.isAfter(thursdayStart.getValue())));
        }
        if (fridayStart.getValue() != null) {
            fridayEnd.setItems(fridayEnd.getItems().filtered(time -> time.isAfter(fridayStart.getValue())));
        }
    }
    
    public void uploadFile(ActionEvent event) throws IOException{  	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Text files", "*.txt")
    			);
    	File file = fileChooser.showOpenDialog(null);
    	
    	if (file != null) {
    		Scanner scanner = new Scanner(file);
    		ArrayList<String> l = new ArrayList<String>();
    		
    		while(scanner.hasNextLine()) {
    			//parse string
    			l.add(scanner.nextLine());
    		}
    		scanner.close();
    		
    		String[] arr = new String[l.size()];
    		
    		for(int i = 0; i < l.size(); i++) {
    			String line = l.get(i).toString().substring(l.get(i).toString().lastIndexOf(": ") + 1);
    			arr[i] = line;
    		}
    		setText(arr);
    		
    	} else {
    		fileWarning.setText("Invalid file");
    	}
    }
    
    public void setText(String[] arr) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
    	
    	//error check semester (Spring, May, Summer, Fall, Winterer) , extra credit (Yes,No)
    	semester.setValue(arr[0]);
    	year.setText(arr[1]);
    	courseSubject.setText(arr[2]);
    	courseNumber.setText(arr[3]);
    	courseName.setText(arr[4]);
    	location.setText(arr[5]);
    	professorName.setText(arr[6]);
    	professorEmail.setText(arr[7]);;
    	extraCredit.setValue(arr[8]);
    	
    	String[] lTime = arr[9].split("[,]",0);
   
    	for(int i = 0; i < lTime.length; i++) {
    		String time = lTime[i].substring(3,lTime[i].length());
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
    	
    }

    public static void setDatabase(Database db) {
        CreateController.db = db;
    }
}
