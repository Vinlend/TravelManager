package ACTBS;

import java.util.*;

public class Flight extends TravelType{
	
	private String origin, destination, fID;
	private int year, day, month;
	private ArrayList<Section> sections = new ArrayList<>(); 
	
	public Flight(String origin, String destination, int year, int day, int month, int hour, int min, String fID) {
		super(origin, destination, year, day, month, hour, min, fID); 
		
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
	
	

	
	
	
	
	
	
}
