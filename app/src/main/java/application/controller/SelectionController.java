package application.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;

/**
 * The SelectionController controls the selection page, which is the "hub" of the application.
 * It has buttons that enable the user to create, view, update, and delete syllabus objects, as
 * well as view their schedule for a given semester and log out of the application.
 */
public class SelectionController {
    @FXML
    private Stage stage;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private Button add;
    @FXML
    private Button schedule;

    /**
     * Event handler for the logout button.
     * @param event
     * @throws IOException
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to log out");

        if (alert.showAndWait().get() == ButtonType.OK){
            root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } 
    }

    /**
     * Event handler for the Add button.
     * @throws IOException
     */
    @FXML
    public void switchToAddScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Create.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event handler for the View button.
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToViewScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/View.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event handler for the Schedule button.
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToSchedule(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Schedule.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show(); 
    }

    /**
     * Event handler for the Delete button.
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToDeleteScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Delete.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show(); 
    }

    /**
     * Event handler for the Update button.
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToUpdate(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Update.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show(); 
    }

}
