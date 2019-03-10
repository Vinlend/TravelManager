package ACTBS;

import java.util.*;

public class Flight {
	
	private String origin, destination, fID;
	private int year, day, month;
	private ArrayList<Section> sections = new ArrayList<>(); 
	
	public Flight(String origin, String destination, int year, int day, int month, String fID) {
		this.fID = fID;
		this.origin = origin;
		this.destination = destination;
		this.year = year;
		this.day = day;
		this.month = month;
	}
	
	public String getOrigin() {
		return this.origin;
	}
	
	public String getDate() {
		return month + "/" + day + "/" + year;
	}
	
	public List<Section> getSections(){
		return this.sections;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public String getfID() {
		return this.fID; 
	}
	
	public boolean bookSeat(SeatClass seatClass, int row, int col) {
		boolean booked = false;
		for(Section i: sections) {
			if(i.getSeatClass().equals(seatClass.name())) {
				booked = i.bookSeat(row, col);
			}
		}
		return booked;
	}
	
	public boolean addSection(Section s) {
		if(this.sections != null) {
			
		
        for(Section s1: this.sections) {
            if(s1.getSeatClass().equals(s.getSeatClass())) {
                throw new IllegalArgumentException("Duplicate Section: " + s1.getSeatClass());
            }
        	}
        	
		}
        sections.add(s);
        return true;

    }
	
	@Override 
	public String toString() {
		String result = fID + " " + this.origin + " " + this.destination + " " + this.getDate(); 
		for(Section s : this.sections)
			result += "\n" + s.toString();
		return result;
	}
	
	
	
	
}
