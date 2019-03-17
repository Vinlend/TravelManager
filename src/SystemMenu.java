import ACTBS.*;
public class SystemMenu {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub


        SystemManager res = new SystemManagerAirports();

        res.createTravelLocation("DEN");
        res.createTravelLocation("DFW");
        res.createTravelLocation("LON");
        res.createTravelLocation("DEN");//invalid
        res.createTravelLocation("DENW");//invalid

        res.createTravelCompany("DELTA");
        res.createTravelCompany("AMER");
        res.createTravelCompany("FRONT");
        res.createTravelCompany("FRONTIER"); //invalid
        res.createTravelCompany("FRONT"); //invalid

        res.createTravelType("DELTA", "DEN", "LON", 2019, 10, 10, "123");
        res.createTravelType("DELTA", "DEN", "DEN", 2019, 8, 8, "567abc");//same airprt
        res.createTravelType("DEL", "DEN", "LON", 2019, 9, 8, "567"); //invalid airline
        res.createTravelType("DELTA", "LON33", "DEN33", 2019, 5, 7, "123");//invalid airports
        res.createTravelType("AMER", "DEN", "LON", 2010, 40, 100, "123abc");//invalid date


        res.createSection("DELTA","123", 2, 2, SeatClass.ECONOMY);
        res.createSection("DELTA","123", 2, 3, SeatClass.FIRST);
        res.createSection("DELTA","123", 2, 3, SeatClass.FIRST);//Invalid
        res.createSection("SWSERTT","123", 5, 5, SeatClass.ECONOMY);//Invalid airline


        res.bookSeat("DELTA", "123", SeatClass.FIRST, 1, 'A');
        res.bookSeat("DELTA", "123", SeatClass.ECONOMY, 1, 'A');
        res.bookSeat("DELTA", "123", SeatClass.ECONOMY, 1, 'B');
        res.bookSeat("DELTA888", "123", SeatClass.BUSINESS, 1, 'A'); //Invalid airline
        res.bookSeat("DELTA", "123haha7", SeatClass.BUSINESS, 1, 'A'); //Invalid flightId
        res.bookSeat("DELTA", "123", SeatClass.ECONOMY, 1, 'A'); //already booked

        res.displaySystemDetails();
        
        res.findAvailableTravels("DEN", "LON", SeatClass.FIRST, 2019, 10, 10);




    }

}
