package application.model;

/**
 * SemesterYear holds the semester and year and returns them if needed
 * @author
 */
public final class SemesterYear implements Comparable<SemesterYear> {
    private final Semester semester;
    private final int year;

    /**
     * Sets private variable semester and year to parameters semester and year  
     * @param semester is used to give it to the specific semester
     * @param year is used to give it to the specific year
     */
    public SemesterYear(Semester semester, int year) {
        this.semester = semester;
        this.year = year;
    }

    /**
     * Returns the private variable semester
     * @return semester
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Returns the private variable year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the semester and year as strings
     * @return semester and year string
     */
    @Override
    public String toString() {
        return this.semester.toString() + " " + this.getYear();
    }

    /**
     * compares SemesterYear with other SemesterYears and returns if they are equal or not
     * @param sy is used to see if the years match and then return if the semesters match or not
     * @return 1, 2, or whatever value that the semesters compare with each other  
     */
    @Override
    public int compareTo(SemesterYear sy) {
        if (this.getYear() > sy.getYear()) {
            return 1;
        } else if (this.getYear() < sy.getYear()) {
            return -1;
        } else {
            return this.getSemester().compareTo(sy.getSemester());
        }
    }

    /**
     * Checks the value of o and uses that to either return true or false
     * @param o is used to check if its equal to the semester and year
     * @return boolean true or false, or the true or false value of year and semester being equal to object o
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        } else if (this == o) {
            return true;
        } else {
            return this.getYear() == ((SemesterYear)o).getYear() 
                && this.getSemester() == ((SemesterYear)o).getSemester();
        }
    }
    
    /**
     * Returns an int of hashCode semester plus the year
     * @return int of hashCode semester plus year
     */
    @Override
    public int hashCode() {
        return this.getSemester().hashCode() + this.getYear();
    }
}
