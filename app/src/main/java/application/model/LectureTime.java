package application.model;

import java.time.LocalTime;
import java.io.Serializable;

/**
 * LectureTime is used to set the times and return the times.
 * @author 
 */
public class LectureTime implements Serializable {
    private LocalTime start;
    private LocalTime end;

    /**
     * Sets the given end and start to the LocalTime variables
     * @param start is set to the LectureTime variable start
     * @param end is set to the LectureTime variable end
     */
    public LectureTime(LocalTime start, LocalTime end) {
        this.setEnd(end);
        this.setStart(start);
    }

    /**
     * Returns the current start
     * @return LocalTime start
     */
    public LocalTime getStart() {
        return start;
    }

    /**
     * Sets the variable start to the parameter start
     * @param start is used to set private variable start to it
     */
    public void setStart(LocalTime start) {
        this.start = start;
    }

    /**
     * Returns the current end
     * @return LocalTime end
     */
    public LocalTime getEnd() {
        return end;
    }

    /**
     * Sets the variable end to the parameter end
     * @param end is used to set private variable end to it
     */
    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
