package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.model.SortSyllabye;
import application.model.Syllabus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewController implements Initializable {
	@FXML
	private TextArea fallText;
	@FXML
	private TextArea mayText;
	@FXML
	private TextArea sprText;
	@FXML
	private TextArea sumText;
	@FXML
	private TextArea winText;	
	@FXML
	private TextField fallYr;
	@FXML
	private TextField sprYr;
	@FXML
	private TextField sumYr;
	@FXML
	private TextField winYr;
	@FXML
	private TextField mayYr;
	
	private static SortSyllabye ss;
	private static HashMap<String, Syllabus> sb;

	 
	public static void setSyllabye (HashMap<String,Syllabus> syllabye) {
		sb = syllabye;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ss = new SortSyllabye(sb);	
	}
	
	public String iterate(Iterator<Syllabus> s) {
		String content = "";
		while (s.hasNext()) {
			Syllabus i = s.next();
			if (ss.validYear(i))
				content += i.toString() + "\n";
		}
		if (content.isBlank())
			return "No available information";
		else
			return content;
	}
	
	@FXML
    public void goSelection(ActionEvent event) throws IOException {
    	//note: nothing will save when the user presses the home button
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectionPage.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.setScene(new Scene(root));
    	stage.show();
    }
	
	@FXML
	public void springInfo() {
		sprText.setVisible(true);
		ss.setYear(Integer.parseInt(sprYr.getText()));
		ss.sortComp(ss.getSpring());
		Iterator<Syllabus> s = ss.getSpring().iterator();
		sprText.setText(iterate(s));
	}
	
	@FXML
	public void mayInfo() {
		mayText.setVisible(true);
		ss.setYear(Integer.parseInt(mayYr.getText()));
		ss.sortComp(ss.getMay());
		Iterator<Syllabus> s = ss.getMay().iterator();
		mayText.setText(iterate(s));
		
	}
	
	@FXML
	public void summerInfo() {
		sumText.setVisible(true);
		ss.setYear(Integer.parseInt(sumYr.getText()));
		ss.sortComp(ss.getSummer());
		Iterator<Syllabus> s = ss.getSummer().iterator();
		sumText.setText(iterate(s));
	}
	
	@FXML
	public void fallInfo() {
		fallText.setVisible(true);
		ss.setYear(Integer.parseInt(fallYr.getText()));
		ss.sortComp(ss.getFall());
		Iterator<Syllabus> s = ss.getFall().iterator();
		fallText.setText(iterate(s));
	}
	
	@FXML
	public void winterInfo() {
		winText.setVisible(true);
		ss.setYear(Integer.parseInt(winYr.getText()));
		ss.sortComp(ss.getWinter());
		Iterator<Syllabus> s = ss.getWinter().iterator();
		winText.setText(iterate(s));
	}

	
}
