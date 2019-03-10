package ACTBS;

import java.time.LocalDate;
import java.util.*;

import ACTBS.SystemExceptions.*;

public class SystemManager {

	public ArrayList<Airport> airports = new ArrayList<Airport>(); 
	public ArrayList<Airline> airlines = new ArrayList<Airline>(); 
	
    public void createAirport(String name) {
    	try {
    		Airport airport = new Airport(name);
        	boolean success = false;
        	for(Airport i: airports) {
        		if(airport.getName().equals(i.getName())){
        			System.out.println("Airport creation failed: Duplicate Airport name");
        			success = true;
        		}
        	}
        	if(!success) {
        	airports.add(airport); 
        	System.out.println("Airport " + airport.getName() + " created successfully"); 
        	}
    	}
    	catch(AirlineNameLengthException e) {
    		System.out.println(e.getMessage());
    	}
    	catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public void createAirline(String name) {
    	try {
    		Airline airline = new Airline(name);
    		boolean success = false;
    		for(Airline i: airlines) {
    			if(airline.getName().equals(i.getName())){
    				System.out.println("Airline creation failed: Duplicate Airline name");
    				success = true;
    			}
    		}
    	
    		if(!success) {
    		airlines.add(airline); 
    		System.out.println("Airline " + airline.getName() + " created successfully");
    		}
    	}
    		catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    	}
    	
    }
    public void createFlight(String aname, String orig, String dest, int year, int month, int day, String fID) {
    	try {
    		if(dest.equals(orig)) {
    			throw new IllegalArgumentException("Flight " + fID + " not created: Origin and Destination are the same"); 
    		}
    		boolean airportNotExist = true;
    		for(Airport ap : this.airports) {
    			if(ap.getName().equals(orig) || ap.getName().equals(dest))
    				airportNotExist = false;
    		}
    		if(airportNotExist)
    			throw new IllegalArgumentException("Flight " + fID + " not created: airport not exist"); 

    		LocalDate localDate = LocalDate.now();
    		if(year < localDate.getYear() ) {
    			throw new IllegalArgumentException("Flight " + fID + " not created: Invalid Date");
    		}
    		if( (month > 12 || day > 31) || (month < 0 || day < 0)) {
    			throw new IllegalArgumentException("Flight " + fID + " not created: Invalid Date");
    		}
    		boolean created = false;
    		for(Airline i: this.airlines) {
    			if(aname.equals(i.getName())) {
    				System.out.println("Flight " + fID + " created");
    				i.addFlight(orig, dest, year, month, day, fID); 
    				created = true;
    			}
    		}
    		if(!created)
    			System.out.println("Flight " + fID + " not created: Airline " + aname + " does not exist!");
    	}
    	catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    	}
    }

    public void createSection(String airline, String flightID, int rows, int cols, SeatClass seatClass) {

    try {
	    	boolean airlineNotFound = true;
	        for(Airline i : this.airlines)
	        {
	        	if(i.getName().equalsIgnoreCase(airline)) {
		        	airlineNotFound = false;
	        		if(i.addFlightSection(flightID, rows, cols, seatClass))
	        			System.out.println(String.format("Section with %s class for %s flight: %s -- Created", seatClass.name(), airline, flightID));
	        		else
	        			System.out.println(String.format("Section with %s class for %s flight: %s -- Failed", seatClass.name(), airline, flightID));
	        	}
	        }
	        if(airlineNotFound)
	        	System.out.println(String.format("Section with %s class for %s flight: %s -- Failed: no such airport", seatClass.name(), airline, flightID));
        }
        catch(RowOutOfBoundsException e){
            System.out.println(String.format("SECTION NOT CREATED :: Airline: %s Flight: %s Class: %s :: REASON: %s", airline, flightID, seatClass, e.getMessage()));
        }
        catch(ColumnOutOfBoundsException e){
            System.out.println(String.format("SECTION NOT CREATED :: Airline: %s Flight: %s Class: %s :: REASON: %s", airline, flightID, seatClass, e.getMessage()));

        }
    }
    
    public void findAvailableFlights(String orig, String dest) {
    	int count = 0; 

		System.out.println("--------Available flights----------");
    	for(Airline i: airlines) {
    		for(Flight j: i.findFlights(orig, dest)){
    			count++;
    			if(j.getSections() == null)
        			System.out.println(String.format("\tAirline: %s Flight: %s Origin: %s Destination: %s Date %s", i.getName(), j.getfID(), j.getOrigin(), j.getDestination(), j.getDate()));
    			else
    			{
    				System.out.println("-----------------------------------");
					System.out.println("Airline: " + i.getName());
    				System.out.println("Origin: " + orig);
    				System.out.println("Destination: " + dest);
    				System.out.println("Date: " + j.getDate());
	    			for(Section s: j.getSections()) {
	    				
	    				if(s.hasAvailableSeats()) {
	    					System.out.println("\n\t" +s.getAvailableSeats());
	    					
	    				}
	    			}
	    			System.out.println("-----------------------------------");
    			}
    		}
    	}
    	
    	if(count == 0) {
    		System.out.println("NO AVAILABLE FLIGHTS"); 
    	}
    }
    public void bookSeat(String airline, String flightID, SeatClass seatClass, int row, char col) {
    	boolean booked = false;
    	for(Airline a : this.airlines) {
    		if(a.getName().equals(airline))
    				booked = a.book(flightID, seatClass, row, col);
    	}

    	if(booked) {
    		System.out.println(String.format("Seat %d%s is booked on %s flight %s in %s class", row, col, airline, flightID, seatClass.name()));
    	}
    	else
    		System.out.println(String.format("Seat %d%s is not booked on %s flight %s in %s class", row, col, airline, flightID, seatClass.name()));
    }
    public void displaySystemDetails() {

    	System.out.println("---System---");
    	System.out.println("Airports:");
    	for(Airport ap : this.airports)
    		System.out.println(String.format("%8s", ap.getName()));

    	System.out.println("Flights:");
    	for(Airline al : this.airlines)
    	{
    		System.out.println(al);
    	}
    		
        

    }
}
