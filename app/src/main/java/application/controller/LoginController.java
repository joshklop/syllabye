package application.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import application.model.Accounts;

public class LoginController {
    @FXML
    private Stage stage;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private PasswordField password;
    @FXML
    private TextField userName;
    @FXML
    private Label warning;
    private static Accounts ac;

    @FXML
    public void switchToSignUpScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToForgotPasswordScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ForgotPassword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSelectionScene(ActionEvent event ) throws IOException, NoSuchAlgorithmException {
        String userN = userName.getText();
        String passW = password.getText();
        ac.loadAccountsInfo();
        if (!(ac.isValidaccount(userN, passW)))
            warning.setText("*Username or password is incorrect*");
        else {
            root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 800);
            stage.setScene(scene);
            stage.show();
        } 
    }

    public static void setAccounts(Accounts accounts) {
        ac = accounts;
    }
}
