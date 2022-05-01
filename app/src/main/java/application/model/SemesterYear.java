package application.model;

public final class SemesterYear implements Comparable<SemesterYear> {
    private final Semester semester;
    private final int year;

    public SemesterYear(Semester semester, int year) {
        this.semester = semester;
        this.year = year;
    }

    public Semester getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return this.semester.toString() + " " + this.getYear();
    }

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
    
    @Override
    public int hashCode() {
        return this.getSemester().hashCode() + this.getYear();
    }
}
