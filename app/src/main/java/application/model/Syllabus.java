package application.model;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

public class Syllabus {
    private String courseName;
    private int courseNumber;
    private String courseSubject;
    private String professorName;
    private Semester semester;
    private int year;
    private String location;
    private String professorEmail;
    private boolean extraCredit;
    private HashMap<DayOfWeek,LectureTime> lectureDayTimes;
    private HashMap<DayOfWeek,RecitationTime> recitationTimes;

    public Syllabus(String courseName, int courseNumber, String courseSubject, 
            String professorName, Semester semester, int year, String location,
            String professorEmail, boolean extraCredit, 
            HashMap<DayOfWeek,LectureTime> lectureDayTimes, HashMap<DayOfWeek, RecitationTime> recitationTimes) {
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
        this.setRecitationTimes(recitationTimes);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(String courseSubject) {
        this.courseSubject = courseSubject;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public boolean isExtraCredit() {
        return extraCredit;
    }

    public void setExtraCredit(boolean extraCredit) {
        this.extraCredit = extraCredit;
    }
    
    public String toString() {
    	String content = courseName + " (" + courseSubject + courseNumber + ")\nProfessor: " + professorName
    			+ "\t\tEmail: " + professorEmail + "\nLocation: " + location + "\n";
    	content += "\tLecture:\n";
    	Object[] keySet = lectureDayTimes.keySet().toArray();
    	Arrays.sort(keySet);
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
    	for (Object d : keySet) {
    		String t1 = lectureDayTimes.get(d).getStart().format(formatter);
    		String t2 = lectureDayTimes.get(d).getEnd().format(formatter);
    		content += "\t\t" + ((DayOfWeek) d).name().substring(0,1).toUpperCase() + ((DayOfWeek) d).name().substring(1).toLowerCase() + " - "; 
    		content += t1 + " to " + t2 + "\n";

    	}
    	content += "\tRecitation:\n";
    	if (recitationTimes.isEmpty())
    		content += "\t\tNo recitation for this course\n";
    	else {
    		keySet = recitationTimes.keySet().toArray();
        	Arrays.sort(keySet);
        	formatter = DateTimeFormatter.ofPattern("h:mma");
        	for (Object d : keySet) {
        		String t1 = recitationTimes.get(d).getRecitationStart().format(formatter);
        		String t2 = recitationTimes.get(d).getRecitationEnd().format(formatter);
        		content += "\t\t" + ((DayOfWeek) d).name().substring(0,1).toUpperCase() + ((DayOfWeek) d).name().substring(1).toLowerCase() + " - "; 
        		content += t1 + " to " + t2 + "\n";

        	}
    	}
    	content += (extraCredit ? "Extra credit is available for this class\n": "No Extra Credit is available for this class\n");
    	return content;
    }

    public HashMap<DayOfWeek,LectureTime> getLectureDayTimes() {
        return lectureDayTimes;
    }

    public void setLectureDayTimes(HashMap<DayOfWeek,LectureTime> lectures) {
        this.lectureDayTimes = lectures;
    }
    
    public HashMap<DayOfWeek,RecitationTime> getRecitationTimes() {
    	return recitationTimes;
    }
    
    public void setRecitationTimes(HashMap<DayOfWeek,RecitationTime> recitations) {
    	this.recitationTimes = recitations;
    }
}
