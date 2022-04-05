package application.controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResetPasswordController {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    public void switchToLoginScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
