package ACTBS;

public class SystemManagerAirports extends SystemManager{

    @Override
    public void createTravelType(String aname, String orig, String dest, int year, int month, int hour, int min, int day, String fID) {
        try {
            super.createTravelType( aname,  orig,  dest,  year,  month,  hour,  min, day, fID);

            boolean travelCompanyCheck = false;
            for(TravelCompany i: this.travelCompanies) {
                if(aname.equals(i.getName())) {
                    travelCompanyCheck = true;
                    if(i.addTravelType(orig, dest, year, month, day, hour, min, fID, TransportationType.FLIGHT)){
                    	System.out.println("Flight " + fID + " created");
                    }
                }
            }
            if(!travelCompanyCheck)
                System.out.println("Flight " + fID + " not created: TravelCompany " + aname + " does not exist!");
        }
        catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
