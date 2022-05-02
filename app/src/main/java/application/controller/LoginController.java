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

/**
 * The LoginController controls the login page and opens
 * the SignUp and ForgotPassword page.
 * This takes the user to the SelectionPage when 
 * they successfully log in to Syllabye.
 */
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
    
    /**
     * The switchToSignUpScene method switches the user to
     * the SignUp page whenever they click the sign-up
     * button.
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToSignUpScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * The switchToForgotPasswordScene method takes the user to the
     * ForgotPassword page whenever they click on the "Forgot 
     * password" link.
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void switchToForgotPasswordScene(ActionEvent event ) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/ForgotPassword.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * The switchToSelectionScene checks if the user has filled out
     * all of the necessary fields and if the password was correct.
     * It will switch the user to the SelectionPage when they successfully 
     * log in.
     * 
     * @param event
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @FXML
    public void switchToSelectionScene(ActionEvent event ) throws IOException, NoSuchAlgorithmException {
        String userN = userName.getText();
        String passW = password.getText();
        ac.loadAccountsInfo();
        if (userN.isBlank() || passW.isBlank())
        	warning.setText("*Please fill in all fields*");
        else if (!(ac.isValidaccount(userN, passW)))
            warning.setText("*Username or password is incorrect*");
        else {
            root = FXMLLoader.load(getClass().getResource("/fxml/Selection.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 800);
            stage.setScene(scene);
            stage.show();
        } 
    }
    
    /**
     * The setAccounts method transfers the Accounts object
     * to this controller.
     * 
     * @param accounts
     */
    public static void setAccounts(Accounts accounts) {
        ac = accounts;
    }
}
