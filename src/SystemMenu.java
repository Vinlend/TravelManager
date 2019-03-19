import ACTBS.SystemManagerAirports;
import ACTBS.SystemManagerCruises;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SystemMenu {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        generalMenu(sc);
        sc.close();
    }

    private static void generalMenu(Scanner sc) {
        Client connectionCruises = new Client(new SystemManagerCruises());
        Client connectionAirports = new Client(new SystemManagerAirports());
        boolean flag = true;
        while(flag) {
            System.out.println(
                    "Menu:\n" +
                    "1. Airport system: create airports, airlines, flights, sections, seats.\n" +
                    "2. Port system: create airports, airlines, flights, sections, seats.\n" +
                    "3. Print Airport System current state.\n" +
                    "4. Print Cruise System current state.\n" +
                    "0. Exit.");


            System.out.println("Choose menu item:");
            int menuItemChosen = readMenuItems(sc);

            switch (menuItemChosen) {
                case 1:
                    airportSysMenu(sc, connectionAirports);
                    break;
                case 2:
                    cruiseSysMenu(sc, connectionCruises);
                    break;
                case 3:
                    connectionAirports.displaySystemDetails();
                    break;
                case 4:
                    connectionCruises.displaySystemDetails();
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }

    }

    private static int readMenuItems(Scanner sc) {
        boolean flag = true;
        int menuItem = -1;
        while(flag) {
            try {
                menuItem = sc.nextInt();
                if (menuItem < 0)
                    throw new IllegalArgumentException("Incorrect menu item: has to be positive number");
                flag = false;
            }
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch(InputMismatchException e) {
                System.out.println("Incorrect menu item: has to be a number");
                sc.nextLine();
            }
        }
        return menuItem;
    }

    private static void airportSysMenu(Scanner sc, Client connectionAirports){
        System.out.println(
                "Airport System Menu:\n" +
                "1. Load system from a file.\n" +
                "2. Change price of the seat class of the flight.\n" +
                "3. Find available seats.\n" +
                "4. Change price of the seat class for specified origin and destination for Airline.\n" +
                "5. Book a specified seat on a flight.\n" +
                "6. Book a seat by a preference.\n" +
                "7. Display airport system.\n" +
                "8. Save airport system in a file.\n" +
                "0. Exit to the previous menu");

        exectuteMenu(sc, connectionAirports);
    }

    private static void cruiseSysMenu(Scanner sc, Client connectionCruises){
        System.out.println(
                "Cruise System Menu:\n" +
                "1. Load system from a file.\n" +
                "2. Change price of the cabin class of the trip.\n" +
                "3. Find available cabins.\n" +
                "4. Change price of the cabin class for specified origin and destination for Cruise.\n" +
                "5. Book a specified cabin on a trip.\n" +
                "6. Book a cabin by a preference.\n" +
                "7. Display Cruise system.\n" +
                "8. Save cruise system in a file.\n" +
                "0. Exit ot the previous menu");

        exectuteMenu(sc, connectionCruises);
    }

    private static void exectuteMenu(Scanner sc, Client connection) {
        boolean flag = true;
        while(flag) {
            System.out.println("Choose menu item:");
            int menuItemChosen = readMenuItems(sc);

            switch (menuItemChosen) {
                case 1:
                    connection.loadFromFile(sc);
                    break;
                case 2:
                    connection.changePriceTrip(sc);
                    break;
                case 3:
                    connection.findTrips(sc);
                    break;
                case 4:
                     connection.changePriceTravel(sc);
                    break;
                case 5:
                     connection.bookSpot(sc);
                    break;
                case 6:
                    connection.bookSpotPreference(sc);
                    break;
                case 7:
                    connection.displaySystemDetails();
                    break;
                case 8:
                    connection.saveToFile(sc);
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }

}

/* res.createTravelLocation("DEN");
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


        res.findAvailableFlights("DEN", "LON");*/
