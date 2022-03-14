package application.controller;

import java.io.File;
import java.io.IOException;

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
        root = FXMLLoader.load(getClass().getResource(File.separator + "fxml" + File.separator + "SignUpPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToForgotPasswordScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource(File.separator + "fxml" + File.separator + "ForgotPasswordPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToSelectionScene(ActionEvent event ) throws IOException {
        String userN = userName.getText();
        String passW = password.getText();
        ac.loadAccountsInfo();
        if (!(ac.isValidaccount(userN, passW)))
            warning.setText("*Username or password is incorrect*");
        else {
            root = FXMLLoader.load(getClass().getResource(File.separator + "fxml" + File.separator + "SelectionPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } 
    }

    public static void setAccounts(Accounts accounts) {
        ac = accounts;
    }
}
