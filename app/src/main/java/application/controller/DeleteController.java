package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.Database;
import application.model.Semester;
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

/**
 * The DeleteController implements the Initializable interface
 * and controls the Delete page.
 * Users will be able to delete courses that they
 * have created or any lecture/recitations.
 *
 */
public class DeleteController implements Initializable {

    @FXML
    private TextField courseNumber;
    @FXML
    private TextField courseSubject;
    @FXML
    private ChoiceBox<String> semester;
    @FXML
    private ChoiceBox<String> delete;
    private ArrayList<String> choices = new ArrayList<String>();
    @FXML
    private TextField year;
    @FXML
    private Label warning;
    @FXML
    private Label warn;
    private static Database db;
    
    /**
     * The initialize method initializes the Delete page
     * and loads the GUI elements with the current
     * Syllabus objects.
     * 
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String sem;
        for (Semester s : Semester.values()) {
            sem = s.toString();
            semester.getItems().add(sem.substring(0, 1).toUpperCase() + sem.substring(1).toLowerCase());
        }
        semester.setValue("Spring");
        choices.add("Delete lecture");
        choices.add("Delete recitation/lab");
        choices.add("Delete both");
        delete.getItems().addAll(choices); 
    }
    
    /**
     * The go Selection method takes the user to the Selection page
     * when they click on the homeButton.
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void goSelection(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    /**
     * The onDelete method removes the chosen object from the Syllabye application.
     * It also error-checks if fields are blank, if the lecture/lab/recitation exists.
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void onDelete(ActionEvent event) throws IOException {
        String subject = courseSubject.getText().toUpperCase().strip();
        String number = courseNumber.getText().strip();
        String sem = semester.getValue().strip();
        String yr = year.getText().strip();
        if (subject.isBlank() || number.isBlank() || yr.isBlank()) {
            warning.setText("Please fill out all fields");
        } else if (delete.getValue() == null) {
            warn.setVisible(true);
        } else if (delete.getValue().equals(choices.get(0))) {
            if (!(db.getSyllabye().containsKey(subject + number + sem + yr + "false"))) {
                warning.setText("This lecture does not exist, please check your inputs ");
            } else {
                db.delete(subject + number + sem + yr + false);
                db.writeSyllabye();
                goSelection(event);
            }
        } else if (delete.getValue().equals(choices.get(1))) {
            if (!(db.getSyllabye().containsKey(subject + number + sem + yr + "true"))) {
                warning.setText("This recitation/lab does not exist, please check your inputs ");
            } else {
                db.delete(subject + number + sem + yr + true);
                db.writeSyllabye();
                goSelection(event);
            }
        }
        else {
            if (!(db.getSyllabye().containsKey(subject + number + sem + yr + "true") &&
            db.getSyllabye().containsKey(subject + number + sem + yr + "false"))) {
                warning.setText("This lecture/recitation/lab does not exist, please check your inputs ");
            } else {
                db.delete(subject + number + sem + yr + false);
                db.delete(subject + number + sem + yr + true);
                db.writeSyllabye();
                goSelection(event);
            }
        }
    }
    
    /**
     * The setSyllabye method transfers the Syllabus data to the
     * DeleteController.
     * 
     * @param db
     */
    public static void setSyllabye(Database db) {
        DeleteController.db = db;
    } 
}
