package application.model;

import java.time.LocalTime;

public class RecitationTime {
	private LocalTime start;
	private LocalTime end;

	public RecitationTime(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}
	
	public void setRecitationStart(LocalTime start) {
		this.start = start;
	}
	
	public LocalTime getRecitationStart() {
		return start;
	}
	
	public void setRecitationEnd(LocalTime end) {
		this.end = end;
	}
	
	public LocalTime getRecitationEnd() {
		return end;
	}
}
