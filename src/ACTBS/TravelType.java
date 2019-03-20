package ACTBS;

import java.util.*;

public abstract class TravelType {
	
	private String origin, destination, ID;
	private int year, day, month, hour, min;
	private ArrayList<Section> sections = new ArrayList<>(); 
	
	public TravelType(String origin, String destination, int year, int day, int month, int hour, int min, String ID) {
		this.ID = ID;
		this.origin = origin;
		this.destination = destination;
		this.year = year;
		this.day = day;
		this.month = month;
		this.hour = hour;
		this.min = min; 
	}
	
	public String getOrigin() {
		return this.origin;
	}
	
	public String getDate() {
		return month + "/" + day + "/" + year;
	}
	
	public int getDay() {
		return day;
	}
	public int getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
	
	public List<Section> getSections(){
		return this.sections;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public String getID() {
		return this.ID; 
	}
	
	//SHould we call this Book or BookSeat??
	public boolean book(SeatClass seatClass, int row, int col) {
		boolean booked = false;
		for(Section i: sections) {
			if(i.getSeatClass().equals(seatClass.name())) {
				booked = i.bookSpot(row, col);
			}
		}
		return booked;
	}

	
	public boolean bookByPreference(SeatClass seatClass, Position position) {
		boolean booked = false;
		for(Section i: sections) {
			if(i.getSeatClass().equals(seatClass.name())) {
				booked = i.bookByPreference(position);
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
		String result = this.ID + "|" + this.year + ", " + this.month + ", " 
				+ this.day + ", " + this.hour + ", " + this.min + "|" + this.origin  + "|" + this.destination + "[";
		
		int sectionNum = 0;
		for(Section section: this.sections) {
			
	    	if(sectionNum != this.sections.size()-1) {
	    		result = result.concat(section.toString() + ",");
	    	} else {
	    		result = result.concat(section.toString());
	    	}
			sectionNum++; 
		}
		
		result = result.concat("]");
		
		return result;
	}

	
	
	
	
	
}
