package ACTBS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import ACTBS.SystemExceptions.*;

public abstract class SystemManager {

	public ArrayList<TravelLocation> travelLocations = new ArrayList<>();
	public ArrayList<TravelCompany> travelCompanies = new ArrayList<>();
	
	private int findTravelCompanyIndex(String travelCompany) {
		int index = -1; 
		for(TravelCompany travel: travelCompanies) {
			if(travel.getName().equals(travelCompany)) {
				index = travelCompanies.indexOf(travel);  
			}
		}
		
		return index;
	}
	
	private int getTravelTypeIndex(String ID, List<TravelType> list) {
		int index = -1;
		for(TravelType travel: list) {
			if(travel.getID().equals(ID)) {
				index = list.indexOf(travel); 
			}
		}
		
		return index;
	}
	
	
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
	    					System.out.println("\n\t" +s.getAvailableSpots());
	    					
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
    public void bookSpot(String travelCompany, String ID, SeatClass seatClass, int row, char col) {
    	boolean booked = false;
    	for(TravelCompany a : this.travelCompanies) {
    		if(a.getName().equals(travelCompany))
    				booked = a.book(ID, seatClass, row, col);
    	}

    	if(booked) {

    		System.out.println(String.format("Spot %d%s is booked on %s TravelType %s in %s class", row, col, travelCompany, ID, seatClass.name()));
    	}
    	else
    		System.out.println(String.format("Spot %d%s is not booked on %s TravelType %s in %s class", row, col, travelCompany, ID, seatClass.name()));

    }

	public void bookSpotPreference(String travelCompany, String ID, SeatClass seatClass, Position position) {
		boolean booked = false;
    	for(TravelCompany a : this.travelCompanies) {
    		if(a.getName().equals(travelCompany))
    				booked = a.bookByPreference(ID, seatClass, position);
    	}

    	if(booked) {

    		System.out.println(String.format("Spot with position " + position.name() + " is booked on %s TravelType %s in %s class", travelCompany, ID, seatClass.name()));
    	}
    	else
    		System.out.println(String.format("Spot with position " + position.name() + " is not booked on %s TravelType %s in %s class", travelCompany, ID, seatClass.name()));
	}
	
	public void changeSpotPriceBySection(String company, String travelID, SeatClass seatClass, double newPrice) {
		boolean companyExist = false;
		for (TravelCompany tc : travelCompanies) {
			if (tc.getName().equalsIgnoreCase(company)) {
				companyExist = true;
				TravelType travelToUpdate = tc.findTravelByID(travelID);
				if (travelToUpdate == null)
					System.out.println("Invalid travel ID");
				else {
					for (Section travelTypeSections : travelToUpdate.getSections())
						if (travelTypeSections.getSeatClass().equals(seatClass.name())) {
							double changedPrice = travelTypeSections.getPrice();
							travelTypeSections.setPrice(newPrice);
							System.out.println(String.format("%s %s in class %s changed from %.2f to %.2f", company, travelID, seatClass.name(), changedPrice, newPrice));
						}
				}
			}
		}
		if(!companyExist)
			System.out.println("Travel company does not exist");
	}
	
	public void changeSpotPriceByOriginDestination(String company, SeatClass seatClass, String origin, String destination, double newPrice) {
		boolean companyExist = false;
		for (TravelCompany tc : travelCompanies) {
			if (tc.getName().equalsIgnoreCase(company)) {
				companyExist = true;
				List<TravelType> travelTypes = tc.findTravelTypesByOriginDestination(origin, destination);
				for(TravelType traveltt : travelTypes) {
					for(Section sectionTravel: traveltt.getSections()) {
						if(sectionTravel.getSeatClass().equals(seatClass.name())) {
							sectionTravel.setPrice(newPrice);
							System.out.println(String.format("Price of %s class for all travels from %s to %s in %s company has been changed to %2.f", seatClass.name(), origin, destination, company, newPrice));
						}
					}
				}
			}
		}

		if(!companyExist)
			System.out.println("Travel company does not exist");
	}
	
	public boolean loadInputFile(Scanner sc) {
		System.out.println("INPUT FILE PATH");
		String filepath = "/Users/coltonsomes/Desktop/EclipseProjects/TravelManager-2/TravelManager/SampleInput.txt";
		//String filepath = sc.nextLine();
		String content = "";
	    try
	    {
	    	TransportationType type = null;
	    	if(this instanceof SystemManagerCruises) {
	    		type = TransportationType.SHIP;
	    	}else if(this instanceof SystemManagerAirports) {
	    		type = TransportationType.FLIGHT;
	    	}
	    	
	    	
	        content = new String ( Files.readAllBytes( Paths.get(filepath) ) );
	        String[] first =  content.split("\\{|\\}"); 
	        System.out.println("ALL AIRPORT NAMES");
	        System.out.println("___________________________________");
	        for(String s: first[0].split("\\[|\\,\\s|\\]")) {
	        	if(s.matches(".*\\w.*")) {
	        		createTravelLocation(s);
	        		System.out.println(s);
	        	}
	        	
	        }
	        
	        String[] second =  first[1].split("\\]\\]");
	        
	        System.out.println("___________________________________");
	        
	        
	        //SECOND is List of Airlines w/ their flights
	        //THIRD is a list of Flights, the first of each will include the airline name
	        String[] third;
	        String airlineName = "";
	        //FOR EACH AIRLINE
	        System.out.println("AIRLINES AND FLIGHTS: ");
	        for(String g: second) {
	        	boolean isFirstFlight = true; 
	        	System.out.println(); 
	        	//String[] third = g.split("\\[|\\,|\\s+|\\||\\]|\\:"); 
	        	third = g.split("\\]\\,"); 
	        	//FOR EACH FLIGHT IN AIRLINE
	        	int flightCount = 0;
	        	for(String h: third) {
	        		flightCount = 0;
	        		h = h.trim(); 
	        		
	        		System.out.println("___________________________________");
	        		//FOURTH will be an array for each Flight listed in Airline
	        		String[] fourth = h.trim().split("\\]\\,\\s|\\[|\\,\\s|\\s+|\\||\\]|\\]\\,");
	        		//If first flight for airline, it will contain the airline name
	        		
	        		if(isFirstFlight) {
	        			isFirstFlight = false;
	        			flightCount++; 
	        		
	        		
		        		if(!fourth[0].matches(".*\\w.*")) {
		        			airlineName = fourth[1];
		        			flightCount++;
		        		} else {
		       				airlineName = fourth[0]; 
		       			}
		        		
		        		createTravelCompany(airlineName); 
		        		
		        		System.out.println("AIRLINE NAME: " + airlineName);
		        		System.out.println("___________________________________");
		        		System.out.println("FLIGHTS ");
		       			System.out.println("___________________________________");
	        		}
	        		//ADD TRAVEL TYPE TO COMPANY LIST
	        		travelCompanies.get(findTravelCompanyIndex(airlineName)).addTravelType(fourth[flightCount+6], fourth[flightCount+7], Integer.parseInt(fourth[flightCount+1]), Integer.parseInt(fourth[flightCount+2]), Integer.parseInt(fourth[flightCount+3]), fourth[flightCount], type);
	       			
	        		
	        		
	        		System.out.println("FID: " + fourth[flightCount]); 
		       		System.out.println("YEAR: " + fourth[flightCount+1]);
		       		System.out.println("MONTH: " + fourth[flightCount+2]);
		       		System.out.println("DAY: " + fourth[flightCount+3]);
		       		System.out.println("HOUR: " + fourth[flightCount+4]);
		       		System.out.println("MIN: " + fourth[flightCount+5]);
		       		System.out.println("ORIG: " + fourth[flightCount+6]);
		       		System.out.println("DEST: " + fourth[flightCount+7]);
	        		
	        		System.out.println("▼▼▼▼FLIGHT SECTIONS▼▼▼▼");
	        		
	        		List<TravelType> travelTypes = travelCompanies.get(findTravelCompanyIndex(airlineName)).getTravelTypes();
	        		TravelType TravelToAddSectionsTo = travelTypes.get(getTravelTypeIndex(fourth[flightCount], travelTypes)); 
	        		
	        		int sectionCount = 0; 
	        		if(fourth[flightCount+8].contains(":")){
	        			String[] flightSections = fourth[flightCount+8].split("\\:|\\,");
	        			
	        			while(sectionCount != flightSections.length ) {
	        				System.out.println("SEATCLASS: " + flightSections[sectionCount]); 
	        				System.out.println("PRICE: " + flightSections[sectionCount+1]); 
	        				System.out.println("LAYOUT: " + flightSections[sectionCount+2]); 
	        				System.out.println("ROWS: " + flightSections[sectionCount+3]); 
	        				System.out.println();
	        				sectionCount += 4; 
	        				
	        				String seatClassString = flightSections[sectionCount+2];
	        				SeatClass seatClassActual;
	        				if(seatClassString.toLowerCase().equals("e")) {
	        					seatClassActual = SeatClass.ECONOMY;
	        				}else if(seatClassString.toLowerCase().equals("b")) {
	        					seatClassActual = SeatClass.BUSINESS;
	        				}else if(seatClassString.toLowerCase().equals("f")){
	        					seatClassActual = SeatClass.FIRST;
	        				} else {
	        					throw new RuntimeException("Seat Class " + seatClassString + " invalid, so section has not been created");
	        				}
	        				
	        				
	        				String layoutString = flightSections[sectionCount+2];
	        				SeatLayout layoutActual;
	        				if(layoutString.toLowerCase().equals("s")){
	        					Section newSection = new Section(Integer.parseInt(flightSections[sectionCount+3]), SeatLayout.SMALL, seatClassActual, Integer.parseInt(flightSections[sectionCount+1]));
	        					TravelToAddSectionsTo.addSection(newSection);
	        				} else if(layoutString.toLowerCase().equals("m")) {
	        					Section newSection = new Section(Integer.parseInt(flightSections[sectionCount+3]), SeatLayout.MEDIUM, seatClassActual, Integer.parseInt(flightSections[sectionCount+1]));
	        					TravelToAddSectionsTo.addSection(newSection);
	        				} else if(layoutString.toLowerCase().equals("w")) {
	        					Section newSection = new Section(Integer.parseInt(flightSections[sectionCount+3]), SeatLayout.WIDE, seatClassActual, Integer.parseInt(flightSections[sectionCount+1]));
	        					TravelToAddSectionsTo.addSection(newSection);
	        				} else {
	        					throw new RuntimeException("Section Layout " + layoutString + " invalid, so section has not been created");
	        				}
	        				
	        				
	        			}
	        		}
        		}	        		
	        	
	        }
	        
	        return true;
	    }
	    catch (IOException e)
	    {
	    	System.out.println("FILE " + filepath + " NOT FOUND");
	        e.printStackTrace();
	        return false; 
	    }
	    catch(IndexOutOfBoundsException e) {
	    	System.out.println("FILE NOT FORMATTED PROPERLY");
	    	return false; 
	    }
		catch(RuntimeException e) {
			System.out.println(e.getMessage());
			
		}
		
	    return true;
		
	}
	
	public boolean saveToFile() {
		return false; 
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
