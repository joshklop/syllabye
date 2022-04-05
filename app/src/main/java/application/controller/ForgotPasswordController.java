package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ForgotPasswordController implements Initializable {
    @FXML
    private Stage stage;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private ChoiceBox<String> securityQuestions;
    private ArrayList<String> questions = new ArrayList<String>();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        questions.add("What's your favorite programming language?");
        questions.add("Who's your favorite teacher");
        questions.add("What was the make of your first car?");
        securityQuestions.getItems().addAll(questions);
    }

    @FXML
    public void switchToLoginScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToResetScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ResetPasswordPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
