package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;

public class MainController {
    @FXML
    private Button nextScene;

    public void clickToNextPane() {
        Stage stage = (Stage) nextScene.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Create.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
    }
}
