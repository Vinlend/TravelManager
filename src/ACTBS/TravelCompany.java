package ACTBS;

import java.util.*;
import ACTBS.SystemExceptions.*;

public class TravelCompany {
	private String name;
	private ArrayList<TravelType> travelList = new ArrayList<TravelType>(); 
	
	public TravelCompany(String name) {
		if(name.length() > 6 || name.length() < 1) {
			throw new AirlineNameLengthException("ERROR: company has not been created: Company name has to be in range(1,5)\n");
		}
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<TravelType> getTravelTypes() {
		return this.travelList; 
		
	}
	
	public ArrayList<TravelType> findTravelTypesByOriginDestination(String origin, String destination) {
		ArrayList<TravelType> travelTypes = new ArrayList<TravelType>();
		for(TravelType i: this.travelList) {
			if(origin.equals(i.getOrigin()) && destination.equals(i.getDestination())) {
				travelTypes.add(i); 
			}
		}
		return travelTypes;
	}
	
	public ArrayList<TravelType> findTravelTypes(String origin, String destination, int year, int month, int day) {
		ArrayList<TravelType> travelTypes = new ArrayList<TravelType>();
		for(TravelType i: this.travelList) {
			if(origin.equals(i.getOrigin()) && destination.equals(i.getDestination()) && year == i.getYear() && month == i.getMonth() && day == i.getDay()) {
				travelTypes.add(i); 
			}
		}
		return travelTypes;
	}

	public TravelType findTravelByID(String ID) {
		for(TravelType tt: travelList) {
			if(tt.getID().equalsIgnoreCase(ID))
				return tt;
		}
		return null;
	}
	
	public boolean bookByPreference(String ID, SeatClass seatClass, Position position) {
		for(TravelType i: travelList) {
			if(ID.equals(i.getID())) {
				return i.bookByPreference(seatClass, position);
			}
		}
		return false; 
	}
	
	public boolean book(String fID, SeatClass seatClass, int row, char col)  {
		for(TravelType i: travelList) {
			if(fID.equals(i.getID())) {
				return i.book(seatClass, row, (Character.toUpperCase(col)-65));
			}
		}
		return false; 
	}

	public boolean addTravelType(String origin, String destination, int year, int month, int day, int hour, int min, String ID, TransportationType type) {
		
		TravelType t;
		try {
			if(type.equals(TransportationType.FLIGHT)) {
				t = new Flight(origin, destination, year, day, month, hour, min, ID);
			} else if(type.equals(TransportationType.SHIP)) {
				t = new Ship(origin, destination, year, day, month, ID);
			} else {
				throw new IllegalArgumentException("ERROR: has not been created.\n");
			}

			for(TravelType i : this.travelList) {
				if(ID.equals(i.getID())) {
					throw new SameFlightIDException("ERROR: has not been created: Duplicate travel ID number.\n");
				}
			}

			this.travelList.add(t);
			return true;
		
		} catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return false;
		}

	}
	
	public boolean addTravelTypeSection(String ID, int rows, SeatLayout seatLayout, SeatClass seatClass, double price) {
		for(TravelType t : this.travelList) {
			if(t.getID().equals(ID)) {
				try {
					boolean flightUnique = true;
					double realPrice = price; 
					for(TravelType tt: this.travelList) {
						if(tt.getOrigin().equals(t.getOrigin()) && tt.getDestination().equals(t.getDestination())) {
							flightUnique = false;
							List<Section> sections = tt.getSections();
							for (Section secP : sections) {
								if(secP.getSeatClass().equalsIgnoreCase(seatClass.name()))
									realPrice = secP.getPrice();
							}
						}
					}
					if(!flightUnique) {
						System.out.println(String.format("Section has been created.\nWarning: s%s has trips with same origin/destination locations: price has been set to %.2f\n", this.getName(), realPrice));
					}
					
					t.addSection(new Section(rows, seatLayout, seatClass, realPrice));
					return true;
				}
				catch(RuntimeException e) {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String result = this.getName() + "[";
		int travelNum = 0;
		for(TravelType travelType : this.travelList) {
			if(travelNum != this.travelList.size()-1) {
				result = result.concat(travelType.toString() + ", ");
	    	} else {
	    		result = result.concat(travelType.toString());
	    	}
			travelNum++;
		}
		result = result.concat("]");
		return result;
	}

	
	
	
}