import ACTBS.SystemManager;
import ACTBS.SeatClass;
public class SampleClients {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub


        SystemManager res = new SystemManager();
        res.createAirport("DEN");
        res.createAirport("DFW");
        res.createAirport("LON");
        res.createAirport("DEN");//invalid
        res.createAirport("DENW");//invalid

        res.createAirline("DELTA");
        res.createAirline("AMER");
        res.createAirline("FRONT");
        res.createAirline("FRONTIER"); //invalid
        res.createAirline("FRONT"); //invalid

        res.createFlight("DELTA", "DEN", "LON", 2019, 10, 10, "123");
        res.createFlight("DELTA", "DEN", "DEN", 2019, 8, 8, "567abc");//same airprt
        res.createFlight("DEL", "DEN", "LON", 2019, 9, 8, "567"); //invalid airline
        res.createFlight("DELTA", "LON33", "DEN33", 2019, 5, 7, "123");//invalid airports
        res.createFlight("AMER", "DEN", "LON", 2010, 40, 100, "123abc");//invalid date


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
        
        res.findAvailableFlights("DEN", "LON");
    }

}
