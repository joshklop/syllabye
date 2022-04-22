package application.model;

public enum Semester {
    SPRING, MAY, SUMMER, FALL, WINTER;

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}

