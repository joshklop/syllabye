package application.model;

import java.time.LocalTime;
import java.io.Serializable;

public class LectureTime implements Serializable {
    private LocalTime start;
    private LocalTime end;

    public LectureTime(LocalTime start, LocalTime end) {
        this.setEnd(end);
        this.setStart(start);
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
