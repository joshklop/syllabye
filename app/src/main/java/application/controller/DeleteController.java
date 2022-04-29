package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.model.Database;
import application.model.Semester;
import application.model.Syllabus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteController implements Initializable {

    @FXML
    private TextField courseNumber;
    @FXML
    private TextField courseSubject;
    @FXML
    private ChoiceBox<String> semester;
    @FXML
    private TextField year;
    @FXML
    private Label warning;
    private static Database db;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	 String sem;
         for (Semester s : Semester.values()) {
             sem = s.toString();
             semester.getItems().add(sem.substring(0, 1).toUpperCase() + sem.substring(1).toLowerCase());
         }
         semester.setValue("Spring");
	}
    
	@FXML
    public void goSelection(ActionEvent event) throws IOException {
       	Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectionPage.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(new Scene(root));
    	stage.show();
    }
	
	@FXML
	public void onDelete(ActionEvent event) throws IOException {
		HashMap<String, Syllabus> syllabye;
		String subject = courseSubject.getText().toUpperCase().strip();
        String number = courseNumber.getText().strip();
        String sem = semester.getValue().strip();
        String yr = year.getText().strip();
        //System.out.println("The set is: " + db.getSyllabye().keySet());
        //System.out.println(subject + number + sem + yr);
        if (subject.isBlank() || number.isBlank() || yr.isBlank())
            warning.setText("Please fill out all fields");
        else if (!(db.getSyllabye().containsKey(subject + number + sem + yr)))
        	warning.setText("This course does not exist, please check your inputs ");
        else {
        	db.delete(subject + number + sem + yr);
        	db.rewriteSyllabye();
        	Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectionPage.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
	}

	public static void setSyllabye(Database db) {
		DeleteController.db = db;
	} 
}
