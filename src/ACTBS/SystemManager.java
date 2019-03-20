package ACTBS;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import ACTBS.SystemExceptions.*;

public abstract class SystemManager {

	public ArrayList<TravelLocation> travelLocations = new ArrayList<>();
	public ArrayList<TravelCompany> travelCompanies = new ArrayList<>();
	
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
    
    public void createTravelType(String aname, String orig, String dest, int year, int month, int day, String ID) {

    		

    		LocalDate localDate = LocalDate.now();
    		if(year < localDate.getYear() ) {
    			throw new IllegalArgumentException("TravelType " + ID + " not created: Invalid Date");
    		}
    		if( (month > 12 || day > 31) || (month < 0 || day < 0)) {
    			throw new IllegalArgumentException("TravelType " + ID + " not created: Invalid Date");
    		}


    }

    public void createSection(String travelCompany, String ID, int rows, SeatLayout seatLayout, SeatClass seatClass, double price) {

    try {
	    	boolean travelLocationNotFound = true;
	        for(TravelCompany i : this.travelCompanies)
	        {
	        	if(i.getName().equalsIgnoreCase(travelCompany)) {
		        	travelLocationNotFound = false;
		        	
		        	
	        		if(i.addTravelTypeSection(ID, rows, seatLayout, seatClass, price))
	        			System.out.println(String.format("Section with %s class for %s travel type: %s -- Created", seatClass.name(), travelCompany, ID));
	        		else
	        			System.out.println(String.format("Section with %s class for %s travel type: %s -- Failed", seatClass.name(), travelCompany, ID));
	        	}
	        }
	        if(travelLocationNotFound)
	        	System.out.println(String.format("Section with %s class for %s TravelType: %s -- Failed: no such travel location", seatClass.name(), travelCompany, ID));
        }
        catch(RowOutOfBoundsException e){
            System.out.println(String.format("SECTION NOT CREATED :: TravelCompany: %s TravelType: %s Class: %s :: REASON: %s", travelCompany, ID, seatClass, e.getMessage()));
        }
        catch(ColumnOutOfBoundsException e){
            System.out.println(String.format("SECTION NOT CREATED :: TravelCompany: %s TravelType: %s Class: %s :: REASON: %s", travelCompany, ID, seatClass, e.getMessage()));

        }
    }
    
    public void findAvailableTravels(String orig, String dest, SeatClass seatClass, int year, int month, int day) {
    	int count = 0; 
		if(year < 2019)
			System.out.println("ERROR: incorrect year\n");
		else if(month < 1 || month > 12)
			System.out.println("ERROR: incorrect month\n");
		else if(day < 1 || day > 31)
			System.out.println("ERROR: incorrect day\n");
		else {
			System.out.println("--------Available ----------");
			for (TravelCompany i : travelCompanies) {
				for (TravelType j : i.findTravelTypes(orig, dest, year, month, day)) {
					count++;
					if (j.getSections() == null)
						System.out.println(String.format("\tTravelCompany: %s TravelType: %s Origin: %s Destination: %s Date %s", i.getName(), j.getID(), j.getOrigin(), j.getDestination(), j.getDate()));
					else {
						System.out.println("-----------------------------------");
						System.out.println("TravelCompany: " + i.getName());
						System.out.println("Origin: " + orig);
						System.out.println("Destination: " + dest);
						System.out.println("Date: " + j.getDate());
						for (Section s : j.getSections()) {

							if (s.hasAvailableSpots()) {
								System.out.println("\n\t" + s.getAvailableSpots());

							}
						}
						System.out.println("-----------------------------------");
					}
				}
			}

			if (count == 0) {
				System.out.println("NO AVAILABLE TRAVEL TYPES");
			}
		}
    }
    public void bookSpot(String travelCompany, String ID, SeatClass seatClass, int row, char col) {
    	col = Character.toUpperCase(col);
    	boolean booked = false;
    	for(TravelCompany a : this.travelCompanies) {
    		if(a.getName().equals(travelCompany))
    				booked = a.book(ID, seatClass, row, col);
    	}

    	System.out.println(String.format("Spot %d%s " + ((booked) ? ("has been booked") : ("has not been booked")) + " on %s %s in %s class", row, col, travelCompany, ID, seatClass.name().toLowerCase()));

    }

	public void bookSpotPreference(String travelCompany, String ID, SeatClass seatClass, Position position) {
		boolean booked = false;
    	for(TravelCompany a : this.travelCompanies) {
    		if(a.getName().equals(travelCompany))
    				booked = a.bookByPreference(ID, seatClass, position);
    	}

    	System.out.println(String.format("Spot by %s" + ((booked) ? ("has been booked") : ("has not been booked")) + " on %s %s in %s class", position.name().toLowerCase(), travelCompany, ID, seatClass.name().toLowerCase()));
    }
	
	public void changeSpotPriceBySection(String company, String travelID, SeatClass seatClass, double newPrice) {
		TravelCompany travelCompanyToUpdate = findCompany(company);
		if(travelCompanyToUpdate == null)
			System.out.println("ERROR: price has not been changed: Travel company does not exist\n");
		else {
			TravelType travelToUpdate = travelCompanyToUpdate.findTravelByID(travelID);
			if (travelToUpdate == null)
				System.out.println("ERROR: price has not been changed: Invalid travel number\n");
			else {
					for (Section travelTypeSections : travelToUpdate.getSections())
						if (travelTypeSections.getSeatClass().equals(seatClass.name())) {
							double changedPrice = travelTypeSections.getPrice();
							travelTypeSections.setPrice(newPrice);
							System.out.println(String.format("%s %s in class %s changed from %.2f to %.2f\n", company, travelID, seatClass.name(), changedPrice, newPrice));
						}
			}
		}
	}
	
	public void changeSpotPriceByOriginDestination(String company, SeatClass seatClass, String origin, String destination, double newPrice) {
		TravelCompany travelCompanyToUpdate = findCompany(company);
		if(travelCompanyToUpdate == null)
			System.out.println("ERROR: price has not been changed: Travel company does not exist\n");
		else {
				List<TravelType> travelTypes = travelCompanyToUpdate.findTravelTypesByOriginDestination(origin, destination);
				for(TravelType traveltt : travelTypes) {
					for(Section sectionTravel: traveltt.getSections()) {
						if(sectionTravel.getSeatClass().equals(seatClass.name())) {
							sectionTravel.setPrice(newPrice);
						}
					}
				}
			System.out.println(String.format("Price of %s class for all travels from %s to %s in %s company has been changed to %.2f\n", seatClass.name(), origin, destination, company, newPrice));
		}
    }

	private TravelCompany findCompany(String companyName) {
		for (TravelCompany tc : travelCompanies) {
			if (tc.getName().equalsIgnoreCase(companyName))
				return tc;
		}
		return null;
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
