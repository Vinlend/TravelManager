package ACTBS;

import java.util.*;

public class Ship extends TravelType{
	
	private String origin, destination, sID;
	private int year, day, month;
	private ArrayList<Section> sections = new ArrayList<>(); 
	
	public Ship(String origin, String destination, int year, int day, int month, String sID) {
		super(origin, destination, year, day, month, sID); 
		
		this.sID = sID;
		this.origin = origin;
		this.destination = destination;
		this.year = year;
		this.day = day;
		this.month = month;
	}
	
	
	
	@Override 
	public String toString() {
		String result = sID + " " + this.origin + " " + this.destination + " " + this.getDate(); 
		for(Section s : this.sections)
			result += "\n" + s.toString();
		return result;
	}
	
	
	
	
}
