package application.model;

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

    public Syllabus(String courseName, int courseNumber, String courseSubject, 
            String professorName, Semester semester, int year, String location,
            String professorEmail, boolean extraCredit) {
        this.setCourseName(courseName);
        this.setCourseNumber(courseNumber);
        this.setCourseSubject(courseSubject);
        this.setProfessorName(professorName);
        this.setSemester(semester);
        this.setYear(year);
        this.setLocation(location);
        this.setProfessorEmail(professorEmail);
        this.setExtraCredit(extraCredit);
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
}
