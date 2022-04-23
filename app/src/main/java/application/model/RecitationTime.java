package application.model;

import java.time.LocalTime;
import java.io.Serializable;

public class RecitationTime implements Serializable{
	private LocalTime start;
	private LocalTime end;

	public RecitationTime(LocalTime start, LocalTime end) {
		this.setRecitationStart(start);
		this.setRecitationEnd(end);
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
