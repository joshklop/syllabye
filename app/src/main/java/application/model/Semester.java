package application.model;

/**
 * Holds the semester types
 * @author
 */
public enum Semester {
    SPRING, MAY, SUMMER, FALL, WINTER;

	/**
	 * Returns the substring of semester
	 */
    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}

