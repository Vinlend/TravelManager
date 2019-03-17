package ACTBS;

public class SystemManagerCruises extends SystemManager {

    @Override
    public void createTravelType(String aname, String orig, String dest, int year, int month, int day, String fID) {
        try {
            super.createTravelType( aname,  orig,  dest,  year,  month,  day,  fID);

            boolean created = false;
            for(TravelCompany i: this.travelCompanies) {
                if(aname.equals(i.getName())) {
                    System.out.println("Cruise " + fID + " created");
                    i.addTravelType(orig, dest, year, month, day, fID, TransportationType.FLIGHT);
                    created = true;
                }
            }
            if(!created)
                System.out.println("Cruse " + fID + " not created: TravelCompany " + aname + " does not exist!");
        }
        catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
