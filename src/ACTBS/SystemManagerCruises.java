package ACTBS;

public class SystemManagerCruises extends SystemManager {

    @Override
    public void createTravelType(String aname, String orig, String dest, int year, int month, int hour, int min, int day, String ID) {
        try {
            super.createTravelType( aname,  orig,  dest,  year,  month,  hour,  min, day, ID);

            boolean created = false;
            for(TravelCompany i: this.travelCompanies) {
                if(aname.equals(i.getName())) {
                    System.out.println("Cruise " + ID + " created");
                    i.addTravelType(orig, dest, year, month, day, hour, min, ID, TransportationType.SHIP);
                    created = true;
                }
            }
            if(!created)
                System.out.println("Cruise " + ID + " not created: TravelCompany " + aname + " does not exist!");
        }
        catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
