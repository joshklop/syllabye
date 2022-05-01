package application.controller;

import application.model.Database;
import application.model.Syllabus;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class UpdateController implements Initializable {
    @FXML
    private ComboBox<Syllabus> syllabusBox;
    @FXML
    private Button updateButton;
    @FXML
    private Button cancelButton;

    private static Database db;

    private static final class SyllabusCell extends ListCell<Syllabus> {
        @Override
        protected void updateItem(Syllabus s, boolean empty) {
            super.updateItem(s, empty);
            if (s == null || empty) {
                setText("");
            } else {
                setText(
                    s.getCourseSubject() + " " + s.getCourseNumber() + " " +
                (s.isRecitation() ? "Recitation " : "") +
                    s.getSemester().toString() + " " + s.getYear()
                );
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        for (Syllabus s : db.getSyllabye().values()) {
            syllabusBox.getItems().add(s);
        }
        syllabusBox.setCellFactory(listView -> new SyllabusCell());
        syllabusBox.setButtonCell(new SyllabusCell());
        updateButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Create.fxml"));
            Parent root = null;
            CreateController.setUpdateMode(true);
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            CreateController cc = loader.getController();
            cc.autofill(syllabusBox.getValue());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 800);
            stage.setScene(scene);
            stage.show(); 
        });
        cancelButton.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 800);
            stage.setScene(scene);
            stage.show(); 
        });
    }

    public static void setDatabase(Database db) {
        UpdateController.db = db;
    }
}
