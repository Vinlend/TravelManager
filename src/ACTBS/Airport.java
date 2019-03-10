package ACTBS;
public class Airport {
	
	private String name; 
	
	public Airport(String name) {
		if(name.length() != 3) {
			throw new IllegalArgumentException("Airport name length not 3"); 
		} else if(!name.matches("[a-zA-Z]+")) {
			throw new IllegalArgumentException("Airport Name cannot contain Numbers or symbols"); 
		}
		
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "Airport name: " + this.name;
	}
}
