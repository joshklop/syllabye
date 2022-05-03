package application.controller;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import application.model.Accounts;
import application.model.Hash;

/**
 * SignUpController is used to display the SignUpPage and controller the functions within it.
 * @author 
 */
public class SignUpController implements Initializable {
    @FXML
    private ChoiceBox<String> securityQuestions;
    @FXML
    private PasswordField confirmPass;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private PasswordField password;
    @FXML
    private TextField securityQuesAns;
    @FXML
    private TextField userName;
    @FXML
    private Label warning;

    private ArrayList<String> questions = new ArrayList<String>();

    private Stage stage;
    private Parent root;
    private Scene scene;

    private static Accounts ac;


    /**
     * Gets the information to start a new account then switches to the login screen if all information is correct
     * @param event is used to set the stage
     * @throws IOException is thrown if an exception of the matching type is found
     * @throws NoSuchAlgorithmException is thrown if an exception of the matching type is found
     */
    @FXML
    public void switchToLoginScene(ActionEvent event ) throws IOException, NoSuchAlgorithmException {
        String firstN = this.firstName.getText();
        String lastN = this.lastName.getText();
        String userN = this.userName.getText();
        String eml = this.email.getText();
        String pass = this.password.getText();
        String cPass = this.confirmPass.getText();
        String sAns = this.securityQuesAns.getText();

        ac.loadAccountsInfo();
        if (ac.isBlank(firstN, lastN, userN, eml, pass, cPass, sAns))
            warning.setText("*Please fill in all fields*");
        else if (!(ac.isPasswordSame(pass, cPass)))
            warning.setText("*Passwords must match*");
        else if (!(ac.isUserNameValid(userN)))
            warning.setText("This username is already taken");
        else {
        	String hashed = new Hash().hash256(cPass);
            ac.appendToFile(firstN, lastN, userN, eml, hashed, sAns);
            root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /**
     * Switches to the login screen
     * @param event is used to set the stage
     * @throws IOException is thrown if an exception of the matching type is found
     */
    @FXML
    public void clickedHome(ActionEvent event) throws IOException {
    	root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the security question answers
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        questions.add("What's your favorite programming language?");
        questions.add("Who's your favorite teacher");
        questions.add("What was the make of your first car?");
        securityQuestions.getItems().addAll(questions);
    }

    /**
     * Sets Accounts ac equal to accounts
     * @param accounts is used to set Accounts ac equal to accounts
     */
    public static void setAccounts(Accounts accounts) {
        ac = accounts;
    }
}
