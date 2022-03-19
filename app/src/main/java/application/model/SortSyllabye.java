package application.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;



public class SortSyllabye {
	private ArrayList<Syllabus> spring;
	private ArrayList<Syllabus> may;
	private ArrayList<Syllabus> fall;
	private ArrayList<Syllabus> summer;
	private ArrayList<Syllabus> winter;
	private int year;
	
	public SortSyllabye (HashMap<String, Syllabus> syllabye) {
		spring = new ArrayList<Syllabus>();
		may = new ArrayList<Syllabus>();
		fall = new ArrayList<Syllabus>();
		summer = new ArrayList<Syllabus>();
		winter = new ArrayList<Syllabus>();
		sort(syllabye);
	}
	
	public void sort(HashMap<String, Syllabus> syllabye) {
		Set<String> keySet = syllabye.keySet();
		for (String key : keySet) {
		    Syllabus s = syllabye.get(key);
		    switch (s.getSemester()) {
		    	case SPRING:
		    		spring.add(s);
		    		break;
		    	case MAY:
		    		may.add(s);
		    		break;
		    	case SUMMER:
		    		summer.add(s);
		    		break;
		    	case FALL:
		    		fall.add(s);
		    		break;
		    	default:
		    		winter.add(s);
		    		break;
		    }
		}
	}
	
	public boolean validYear(Syllabus s) {
		return s.getYear() == this.year;
	}
	
	
	public ArrayList<Syllabus> getSpring() {
		return spring;
	}
	
	public void setSpring(ArrayList<Syllabus> spring) {
		this.spring = spring;
	}
	
	public ArrayList<Syllabus> getMay() {
		return may;
	}
	
	public void setMay(ArrayList<Syllabus> may) {
		this.may = may;
	}
	
	public ArrayList<Syllabus> getFall() {
		return fall;
	}
	
	public void setFall(ArrayList<Syllabus> fall) {
		this.fall = fall;
	}
	
	public ArrayList<Syllabus> getSummer() {
		return summer;
	}
	
	public void setSummer(ArrayList<Syllabus> summer) {
		this.summer = summer;
	}
	
	public ArrayList<Syllabus> getWinter() {
		return winter;
	}
	
	public void setWinter(ArrayList<Syllabus> winter) {
		this.winter = winter;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
