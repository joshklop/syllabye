package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import application.model.SortSyllabye;
import application.model.Syllabus;


public class ScheduleController {
	@FXML
	Label test;
	@FXML
	Button load;
	
	private static HashMap<String, Syllabus> sb;

	public void initialize() {
		test.setText("Testing");
	}
	
	@FXML
	public void loadSchedule(ActionEvent event) {
		/**TODO:
		 * Calculate the # of rows depending on the earliest class & latest class, then
		 * script grid pane based on that
		 * Have rectangles sent to the back as a way to color code, or just change the cell's
		 * background color
		 * Only show created syllabuses in the combo boxes
		 * Find better color scheme
		 */
	
		test.setText("Clicked");
		
		GridPane grid = new GridPane();
		Label label = new Label();
		GridPane.setConstraints(label, 2, 2);
		GridPane.setRowIndex(label, 1);
		GridPane.setColumnIndex(label, 1);
		grid.getChildren().addAll(label);
		
	}
	
	public static void setSyllabye(HashMap<String, Syllabus> syllabye) {
		sb = syllabye;
	}	
}
