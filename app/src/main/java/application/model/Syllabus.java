package application.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Syllabus keeps the data for each syllabus and can set and return them if needed
 * @author 
 */
public class Syllabus implements Serializable {
    private String courseName;
    private Integer courseNumber;
    private String courseSubject;
    private String professorName;
    private Semester semester;
    private Integer year;
    private String location;
    private String professorEmail;
    private Boolean extraCredit;
    private HashMap<DayOfWeek,LectureTime> lectureDayTimes;
    private Boolean recitation;

    /**
     * Sets each parameter given to the private variables
     * @param courseName is set to private courseName
     * @param courseNumber is set to private courseNumber
     * @param courseSubject is set to private courseSubject
     * @param professorName is set to private professorName
     * @param semester is set to private semester
     * @param year is set to private year
     * @param location is set to private location
     * @param professorEmail is set to private professorEmail
     * @param extraCredit is set to private extraCredit
     * @param lectureDayTimes is set to private lectureDayTimes
     * @param recitation is set to private recitation
     */
    public Syllabus(String courseName, Integer courseNumber, String courseSubject, 
            String professorName, Semester semester, Integer year, String location,
            String professorEmail, Boolean extraCredit, 
            HashMap<DayOfWeek,LectureTime> lectureDayTimes, Boolean recitation) {
        this.setCourseName(courseName);
        this.setCourseNumber(courseNumber);
        this.setCourseSubject(courseSubject);
        this.setProfessorName(professorName);
        this.setSemester(semester);
        this.setYear(year);
        this.setLocation(location);
        this.setProfessorEmail(professorEmail);
        this.setExtraCredit(extraCredit);
        this.setLectureDayTimes(lectureDayTimes);
        this.setRecitation(recitation);
    }

    /**
     * Used to compare two syllabi
     */
    public static Comparator<Syllabus> SyllabusComparator = new Comparator<Syllabus>() {
    	
    	/**
    	 * Compares the subject and number score of syllabus1 with syllabus2
    	 * @param syllabus1 compares with syllabus2
    	 * @param syllabus2 compares with syllabus1
    	 * @return combined int of subjectScore and numberScore
    	 */
    	public int compare(Syllabus syllabus1, Syllabus syllabus2) {
    		int subjectScore = syllabus1.getCourseSubject().compareToIgnoreCase(syllabus2.getCourseSubject());
    		int numberScore = Integer.compare(syllabus1.getCourseNumber(), syllabus2.getCourseNumber());
    		return subjectScore + numberScore;
    	}
    };

    /**
     * Gets the courseName
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets private courseName to parameter courseName
     * @param courseName sets to private courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the courseSubject
     * @return courseSubject
     */
    public String getCourseSubject() {
        return courseSubject;
    }

    /**
     * Sets private courseSubject to parameter courseSubject
     * @param courseSubject sets to private courseSubject
     */
    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    /**
     * Gets the courseNumber
     * @return courseNumber
     */
    public Integer getCourseNumber() {
        return courseNumber;
    }

    /**
     * Sets private courseNumber to parameter courseNumber
     * @param courseNumber sets to private courseNumber
     */
    public void setCourseNumber(Integer courseNumber) {
        this.courseNumber = courseNumber;
    }

    /**
     * Gets the professorName
     * @return professorName
     */
    public String getProfessorName() {
        return professorName;
    }

    /**
     * Sets private professorName to parameter professorName
     * @param professorName sets to private professorName
     */
    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    /**
     * Gets the semester
     * @return semester
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Sets private semester to parameter semester
     * @param semester sets to private semester
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Gets the year
     * @return year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets private year to parameter year
     * @param year sets to private year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets the location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets private location to parameter location
     * @param location sets to private location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the professorEmail
     * @return professorEmail
     */
    public String getProfessorEmail() {
        return professorEmail;
    }

    /**
     * Sets private professorEmail to parameter professorEmail
     * @param professorEmail sets to private professorEmail
     */
    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    /**
     * Gets the extraCredit
     * @return extraCredit
     */
    public Boolean isExtraCredit() {
        return extraCredit;
    }

    /**
     * Sets private extraCredit to parameter extraCredit
     * @param extraCredit sets to private extraCredit
     */
    public void setExtraCredit(Boolean extraCredit) {
        this.extraCredit = extraCredit;
    }
    
    /**
     *	Returns the content of a syllabus for the view page. 
     */
    public String toString() {
    	String courseType = recitation ? "Recitation" : "Lecture";
    	String content = courseName + " (" + courseSubject + courseNumber + ") - " + courseType + "\n\tProfessor: " + professorName
    			+ "\t\tEmail: " + professorEmail + "\n\tLocation: " + location + "\n";
    	content += "\tScheduled Meeting Times\n";
    	Object[] keySet = lectureDayTimes.keySet().toArray();
    	Arrays.sort(keySet);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
    	for (Object d : keySet) {
    		String t1 = lectureDayTimes.get(d).getStart().format(formatter);
    		String t2 = lectureDayTimes.get(d).getEnd().format(formatter);
    		content += "\t\t\t- " + ((DayOfWeek) d).name().substring(0,1).toUpperCase() + ((DayOfWeek) d).name().substring(1).toLowerCase() + " - "; 
    		content += t1 + " to " + t2 + "\n";

    	}
    	content += (extraCredit ? "\tExtra credit is available for this class\n": "\tNo Extra Credit is available for this class\n");
    	return content;
    }

    /**
     * gets the lectureDayTimes
     * @return lectureDayTimes
     */
    public HashMap<DayOfWeek,LectureTime> getLectureDayTimes() {
        return lectureDayTimes;
    }

    /**
     * sets the lectureDayTimes to given parameter
     * @param lectures is used to set lectureDayTimes to it
     */
    public void setLectureDayTimes(HashMap<DayOfWeek,LectureTime> lectures) {
        this.lectureDayTimes = lectures;
    }

    /**
     * checks to see if recitation is true or false
     * @return if recitation is true or false
     */
    public Boolean isRecitation() {
        return recitation;
    }

    /**
     * sets the recitation parameter to the private recitation
     * @param recitation is used to set private recitation to it
     */
    public void setRecitation(Boolean recitation) {
        this.recitation = recitation;
    }
}
