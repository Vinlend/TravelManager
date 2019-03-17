package ACTBS;

import java.time.LocalDate;
import java.util.*;

import ACTBS.SystemExceptions.*;

public abstract class SystemManager {

	public ArrayList<TravelLocation> travelLocations = new ArrayList<TravelLocation>(); 
	public ArrayList<TravelCompany> travelCompanies = new ArrayList<TravelCompany>(); 
	
    public void createTravelLocation(String name) {
    	try {
    		TravelLocation travelLocation = new TravelLocation(name);
        	boolean success = false;
        	for(TravelLocation i: travelLocations) {
        		if(travelLocation.getName().equals(i.getName())){
        			System.out.println("TravelLocation creation failed: Duplicate TravelLocation name");
        			success = true;
        		}
        	}
        	if(!success) {
        		travelLocations.add(travelLocation); 
        	System.out.println("TravelLocation " + travelLocation.getName() + " created successfully"); 
        	}
    	}
    	catch(AirlineNameLengthException e) {
    		System.out.println(e.getMessage());
    	}
    	catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    	}
    }
    
    public void createTravelCompany(String name) {
    	try {
    		TravelCompany airline = new TravelCompany(name);
    		boolean success = false;
    		for(TravelCompany i: travelCompanies) {
    			if(airline.getName().equals(i.getName())){
    				System.out.println("TravelCompany creation failed: Duplicate TravelCompany name");
    				success = true;
    			}
    		}
    	
    		if(!success) {
    		travelCompanies.add(airline); 
    		System.out.println("TravelCompany " + airline.getName() + " created successfully");
    		}
    	}
    		catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    	}
    	
    }
    public void createTravelType(String aname, String orig, String dest, int year, int month, int day, String fID) {

    		

    		LocalDate localDate = LocalDate.now();
    		if(year < localDate.getYear() ) {
    			throw new IllegalArgumentException("Flight " + fID + " not created: Invalid Date");
    		}
    		if( (month > 12 || day > 31) || (month < 0 || day < 0)) {
    			throw new IllegalArgumentException("Flight " + fID + " not created: Invalid Date");
    		}


    }

    public void createSection(String travelCompany, String ID, int rows, int cols, SeatClass seatClass, double price) {

    try {
	    	boolean travelLocationNotFound = true;
	        for(TravelCompany i : this.travelCompanies)
	        {
	        	if(i.getName().equalsIgnoreCase(travelCompany)) {
		        	travelLocationNotFound = false;
		        	
		        	
	        		if(i.addTravelTypeSection(ID, rows, cols, seatClass, price))
	        			System.out.println(String.format("Section with %s class for %s travel type: %s -- Created", seatClass.name(), travelCompany, ID));
	        		else
	        			System.out.println(String.format("Section with %s class for %s travel type: %s -- Failed", seatClass.name(), travelCompany, ID));
	        	}
	        }
	        if(travelLocationNotFound)
	        	System.out.println(String.format("Section with %s class for %s flight: %s -- Failed: no such travel location", seatClass.name(), travelCompany, ID));
        }
        catch(RowOutOfBoundsException e){
            System.out.println(String.format("SECTION NOT CREATED :: TravelCompany: %s Flight: %s Class: %s :: REASON: %s", travelCompany, ID, seatClass, e.getMessage()));
        }
        catch(ColumnOutOfBoundsException e){
            System.out.println(String.format("SECTION NOT CREATED :: TravelCompany: %s Flight: %s Class: %s :: REASON: %s", travelCompany, ID, seatClass, e.getMessage()));

        }
    }
    
    public void findAvailableTravels(String orig, String dest, SeatClass seatClass, int year, int month, int day) {
    	int count = 0; 

		System.out.println("--------Available ----------");
    	for(TravelCompany i: travelCompanies) {
    		for(TravelType j: i.findTravelTypes(orig, dest, year, month, day)){
    			count++;
    			if(j.getSections() == null)
        			System.out.println(String.format("\tTravelCompany: %s TravelType: %s Origin: %s Destination: %s Date %s", i.getName(), j.getID(), j.getOrigin(), j.getDestination(), j.getDate()));
    			else
    			{
    				System.out.println("-----------------------------------");
					System.out.println("TravelCompany: " + i.getName());
    				System.out.println("Origin: " + orig);
    				System.out.println("Destination: " + dest);
    				System.out.println("Date: " + j.getDate());
	    			for(Section s: j.getSections()) {
	    				
	    				if(s.hasAvailableSpots()) {
	    					System.out.println("\n\t" +s.getAvailableSports());
	    					
	    				}
	    			}
	    			System.out.println("-----------------------------------");
    			}
    		}
    	}
    	
    	if(count == 0) {
    		System.out.println("NO AVAILABLE TRAVEL TYPES"); 
    	}
    }
    public void bookSeat(String travelCompany, String flightID, SeatClass seatClass, int row, char col) {
    	boolean booked = false;
    	for(TravelCompany a : this.travelCompanies) {
    		if(a.getName().equals(travelCompany))
    				booked = a.book(flightID, seatClass, row, col);
    	}

    	if(booked) {

    		System.out.println(String.format("Spot %d%s is booked on %s flight %s in %s class", row, col, travelCompany, flightID, seatClass.name()));
    	}
    	else
    		System.out.println(String.format("Spot %d%s is not booked on %s flight %s in %s class", row, col, travelCompany, flightID, seatClass.name()));

    }

	public void bookSeatPreference(String travelCompany, String flightID, SeatClass seatClass, int row, char col) {
	}
	
	public void changeSpotPriceBySection(TravelType travelType, SeatClass seatClass, double newPrice) {
		List<Section> sections = travelType.getSections(); 
		for(Section s: sections) {
			if(s.getSeatClass().equals(seatClass.name())) {
				s.setPrice(newPrice); 
				System.out.println(seatClass.name() + "section price changed to " + newPrice);
			}
		}
	}
	
	public void changeSpotPriceByOriginDestination(TravelCompany travelCompany, SeatClass seatClass, String origin, String destination, double newPrice) {
		List<TravelType> travelTypes = travelCompany.findTravelTypesByOriginDestination(origin, destination); 
		for(TravelType t: travelTypes) {
			changeSpotPriceBySection(t, seatClass, newPrice); 
		}
	}
    public void displaySystemDetails() {

    	System.out.println("---System---");
    	System.out.println("TravelLocations:");
    	for(TravelLocation ap : this.travelLocations)
    		System.out.println(String.format("%8s", ap.getName()));

    	System.out.println("Flights:");
    	for(TravelCompany al : this.travelCompanies)
    	{
    		System.out.println(al);
    	}
    		
        

    }
}
