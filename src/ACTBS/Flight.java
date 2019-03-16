package ACTBS;

import java.util.*;

public class Flight extends TravelType{
	
	private String origin, destination, fID;
	private int year, day, month;
	private ArrayList<Section> sections = new ArrayList<>(); 
	
	public Flight(String origin, String destination, int year, int day, int month, String fID) {
		super(origin, destination, year, day, month, fID); 
		
		if(destination .equals(origin)) {
			throw new IllegalArgumentException("Flight " + fID + " not created: Origin and Destination are the same"); 
		}
		
		this.fID = fID;
		this.origin = origin;
		this.destination = destination;
		this.year = year;
		this.day = day;
		this.month = month;
	}
	
	
	
	@Override 
	public String toString() {
		String result = fID + " " + this.origin + " " + this.destination + " " + this.getDate(); 
		for(Section s : this.sections)
			result += "\n" + s.toString();
		return result;
	}
	
	
	
	
}
