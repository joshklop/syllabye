package application.controller;

import java.util.HashMap;
import java.util.Iterator;

import application.model.SortSyllabye;
import application.model.Syllabus;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ViewController {
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
	 
	public static void setSyllabye (HashMap<String,Syllabus> syllabye) {
		ss = new SortSyllabye(syllabye);
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
	public void springInfo() {
		sprText.setVisible(true);
		ss.setYear(Integer.parseInt(sprYr.getText()));
		Iterator<Syllabus> s = ss.getSpring().iterator();
		sprText.setText(iterate(s));
	}
	
	@FXML
	public void mayInfo() {
		mayText.setVisible(true);
		ss.setYear(Integer.parseInt(mayYr.getText()));
		Iterator<Syllabus> s = ss.getMay().iterator();
		mayText.setText(iterate(s));
		
	}
	
	@FXML
	public void summerInfo() {
		sumText.setVisible(true);
		ss.setYear(Integer.parseInt(sumYr.getText()));
		Iterator<Syllabus> s = ss.getSummer().iterator();
		sumText.setText(iterate(s));
	}
	
	@FXML
	public void fallInfo() {
		fallText.setVisible(true);
		ss.setYear(Integer.parseInt(fallYr.getText()));
		Iterator<Syllabus> s = ss.getFall().iterator();
		fallText.setText(iterate(s));
	}
	
	@FXML
	public void winterInfo() {
		winText.setVisible(true);
		ss.setYear(Integer.parseInt(winYr.getText()));
		Iterator<Syllabus> s = ss.getWinter().iterator();
		winText.setText(iterate(s));
	}
}
