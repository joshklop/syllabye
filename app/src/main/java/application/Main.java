package application;

import java.io.IOException;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.EOFException;
import application.controller.UpdateController;
import application.controller.ScheduleController;
import application.controller.LoginController;
import application.controller.SignUpController;
import application.controller.ViewController;
import application.controller.CreateController;
import application.controller.DeleteController;
import application.controller.ForgotPasswordController;
import application.model.Database;
import application.model.Accounts;

/**
 * The Main class is used to start the login screen and sets all the controllers with the database and accounts
 * @author 
 */
public class Main extends Application {

	/**
	 * Sets up and shows the FXML Login page
	 */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Syllabye");
        primaryStage.show();
    }

    /**
     * Connects the controllers with the database and accounts
     * @param args is used to call launch
     * @throws FileNotFoundException if there is an exception of the matching type
     * @throws IOException if there is an exception of the matching type
     * @throws ClassNotFoundException if there is an exception of the matching type
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Database db = new Database(Main.class.getResource("/data/syllabyeDB"));
        try {
            db.readSyllabye();
        } catch (EOFException e) { // If file does not exist
            db.resetSyllabye();
        }
        CreateController.setDatabase(db);
        ViewController.setSyllabye(db.getSyllabye());
        DeleteController.setSyllabye(db);
        UpdateController.setDatabase(db);
        ScheduleController.setDatabase(db);
        Accounts ac = new Accounts();
        SignUpController.setAccounts(ac);
        LoginController.setAccounts(ac);
        ForgotPasswordController.setAccounts(ac);
        launch(args);
    }
}
