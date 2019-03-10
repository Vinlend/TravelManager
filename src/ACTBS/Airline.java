package ACTBS;

import java.util.*;
import ACTBS.SystemExceptions.*;

public class Airline {
	private String name;
	private ArrayList<Flight> flightList = new ArrayList<Flight>(); 
	
	public Airline(String name) {
		if(name.length() > 6 || name.length() < 1) {
			throw new AirlineNameLengthException("Airline name is greater than 5 char OR les than 1 char");
		}
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Flight> findFlights(String origin, String destination) {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		for(Flight i: this.flightList) {
			if(origin.equals(i.getOrigin()) && destination.equals(i.getDestination())) {
				flights.add(i); 
			}
		}
		return flights;
	}
	
	public boolean book(String fID, SeatClass seatClass, int row, char col)  {
		for(Flight i: flightList) {
			if(fID.equals(i.getfID())) {
				return i.bookSeat(seatClass, row, colToNum(col));
			}
		}
		return false; 
	}
	
	public boolean addFlight(String origin, String destination, int year, int month, int day, String fID) {
		Flight f = new Flight(origin, destination, year, day, month, fID);
		for(Flight i : this.flightList) {
			if(fID.equals(i.getfID())) {
				throw new SameFlightIDException("Duplicate Flight ID"); 
			}
		}
		this.flightList.add(f); 
		return true;
	}
	
	public boolean addFlightSection(String flightID, int rows, int cols, SeatClass seatClass) {
		for(Flight f : this.flightList) {
			if(f.getfID().equals(flightID)) {
				try {
				f.addSection(new Section(rows, cols, seatClass));
				return true;
				}
				catch(RuntimeException e) {
					return false;
				}
			}
		}
		return false;
	}
	
	private int colToNum(char col) {
		int colNum = 0;
		switch(Character.toLowerCase(col)) {
        	case 'a' :
        		colNum = 0;
        		break;
        	case 'b' :
        		colNum = 1;
        		break;
        	case 'c' :
        		colNum = 2;
        		break;
        	case 'd' :
        		colNum = 3;
        		break;
        	case 'e' :
        		colNum = 4;
        		break;
        	case 'f' :
        		colNum = 5;
        		break;
        	case 'g' :
        		colNum = 6;
        		break;
        	case 'h' :
        		colNum = 7;
        		break;
        	case 'i' :
        		colNum = 8;
        		break;
        	case 'j' :
        		colNum = 9;
        		break;
        	default :
        		break;
		}
		return colNum;
		
	}
	
	@Override 
	public String toString() {
		String result = "";
		for(Flight f : this.flightList) {
			result += String.format("%10s %s", this.name, f.toString());
		}
		return result;
	}
}