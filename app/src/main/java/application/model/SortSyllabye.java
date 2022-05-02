package application.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * SortSyllabye is used to sort the syllabye by semester and year
 * @author
 */
public class SortSyllabye {
	private ArrayList<Syllabus> spring;
	private ArrayList<Syllabus> may;
	private ArrayList<Syllabus> fall;
	private ArrayList<Syllabus> summer;
	private ArrayList<Syllabus> winter;
	private int year;
	
	/**
	 * SortSyllabye initializes the semester ArrayLists and calls sort 
	 * @param syllabye is used to call sort
	 */
	public SortSyllabye (HashMap<String, Syllabus> syllabye) {
		spring = new ArrayList<Syllabus>();
		may = new ArrayList<Syllabus>();
		fall = new ArrayList<Syllabus>();
		summer = new ArrayList<Syllabus>();
		winter = new ArrayList<Syllabus>();
		sort(syllabye);
	}
	
	/**
	 * Sort is used to add the corresponding semester for s Syllabus and add s to each semester
	 * @param syllabye is used to get the key set and the chosen Syllabus
	 */
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
	
	/**
	 * Sorts the parameter s
	 * @param s is sorted
	 */
	public void sortComp(ArrayList<Syllabus> s) {
		s.sort(Syllabus.SyllabusComparator);
	}
	
	/**
	 * Checks to see if given year matches current year set
	 * @param s is used to get the year of s
	 * @return boolean true or false based on whenever or not the years matches 
	 */
	public boolean validYear(Syllabus s) {
		return s.getYear() == this.year;
	}
	
	/**
	 * Returns the spring arrayList
	 * @return spring
	 */
	public ArrayList<Syllabus> getSpring() {
		return spring;
	}
	
	/**
	 * Sets private spring arrayList to the given spring
	 * @param spring is used to set private spring to it
	 */
	public void setSpring(ArrayList<Syllabus> spring) {
		this.spring = spring;
	}
	
	/**
	 * Returns the may arrayList
	 * @return may
	 */
	public ArrayList<Syllabus> getMay() {
		return may;
	}
	
	/**
	 * Sets private may arrayList to the given may
	 * @param may is used to set private may to it
	 */
	public void setMay(ArrayList<Syllabus> may) {
		this.may = may;
	}
	
	/**
	 * Returns the fall arrayList
	 * @return fall
	 */
	public ArrayList<Syllabus> getFall() {
		return fall;
	}
	
	/**
	 * Sets private fall arrayList to the given fall
	 * @param fall is used to set private fall to it
	 */
	public void setFall(ArrayList<Syllabus> fall) {
		this.fall = fall;
	}
	
	/**
	 * Returns the summer arrayList
	 * @return summer
	 */
	public ArrayList<Syllabus> getSummer() {
		return summer;
	}
	
	/**
	 * Sets private summer arrayList to the given summer
	 * @param summer is used to set private summer to it
	 */
	public void setSummer(ArrayList<Syllabus> summer) {
		this.summer = summer;
	}
	
	/**
	 * Returns the winter arrayList
	 * @return winter
	 */
	public ArrayList<Syllabus> getWinter() {
		return winter;
	}
	
	/**
	 * Sets private winter arrayList to the given winter
	 * @param winter is used to set private winter to it
	 */
	public void setWinter(ArrayList<Syllabus> winter) {
		this.winter = winter;
	}

	/**
	 * returns the set year
	 * @return year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * sets the parameter year to the private year
	 * @param year is used to set private year to parameter year
	 */
	public void setYear(int year) {
		this.year = year;
	}
}
