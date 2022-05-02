package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.model.Accounts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The ForgotPasswordController class is used with the ForogtPasswordPage fxml to change the password of a login account
 * 
 * @author
 */
public class ForgotPasswordController implements Initializable {
    @FXML
    private Stage stage;
    @FXML
    private Parent root;
    @FXML
    private Scene scene;
    @FXML
    private ChoiceBox<String> securityQuestions;
    @FXML
    private TextField userName;
    @FXML
    private TextField securityQuesAns;
    @FXML
    private TextField email;
    @FXML
    private Label warning;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    
    private ArrayList<String> questions = new ArrayList<String>();
    
    private static Accounts ac = new Accounts();

    /**
     * Initializes the questions for the forget password
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        questions.add("What's your favorite programming language?");
        questions.add("Who's your favorite teacher");
        questions.add("What was the make of your first car?");
        securityQuestions.getItems().addAll(questions);
    }

    /**
     * Switches to login scene when called
     * @param event used to set the stage and the scene
     * @throws IOException if there is an exception
     */
    @FXML
    public void switchToLoginScene(ActionEvent event ) throws IOException {
        //root = FXMLLoader.load(getClass().getResource(File.separator + "fxml" + File.separator + "LoginPage.fxml"));
    	root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Verifies an account and then changes the password if it is verified
     * @param event used to set the stage and scene
     * @throws IOException if there is an exception of the matching type
     * @throws NoSuchAlgorithmException if there is an exception of the matching type
     */
    @FXML
    public void switchToResetScene(ActionEvent event ) throws IOException, NoSuchAlgorithmException {
    	String userN = this.userName.getText();
        String eml = this.email.getText();
        String sAns = this.securityQuesAns.getText();
        String newPass = this.password.getText();
        String newCPass = this.confirmPassword.getText();
        
        ac.loadAccountsInfo();
        
        if(userN.isBlank() || eml.isBlank() || sAns.isBlank() || newPass.isBlank() || newCPass.isBlank())
        {
            warning.setText("*Please fill in all fields*");
        }
        else if(!(ac.isPasswordRecoveryAccountValid(userN, eml, sAns)))
        {
        	warning.setText("*Account is not valid*");
        }
        else if(!(ac.isPasswordSame(newPass, newCPass)))
        {
        	warning.setText("*Passwords must match*");
        }
        else
        {
        	ac.changePassword(userN, eml, sAns, newPass);
        	//root = FXMLLoader.load(getClass().getResource(File.separator + "fxml" + File.separator + "LoginPage.fxml"));
        	root = FXMLLoader.load(getClass().getResource("/fxml/LoginPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Connects the controller with the accounts database to make sure the password is changed
     * @param accounts used to connect with the ForgotPasswordController with the rest of the program
     */
	public static void setAccounts(Accounts accounts) {
		ForgotPasswordController.ac = accounts;
		
	}
}
