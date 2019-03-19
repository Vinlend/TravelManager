import ACTBS.SeatClass;
import ACTBS.SystemManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {
    SystemManager manager;

    public Client(SystemManager manager) {
        this.manager = manager;
    }

    public void displaySystemDetails() {
        manager.displaySystemDetails();
    }

    public void loadFromFile(Scanner sc) {
        System.out.println("Load from file.");
        sc.nextLine();
        boolean stop = false;
        while(!stop) {
            try {
                System.out.println("Enter the path to the file or type 0 to exit:");
                String filePath = sc.nextLine();
                if (filePath.equalsIgnoreCase("0"))
                    stop = true;
                else {
                    Scanner file = new Scanner(new File(filePath));
                }
                //manager.loadFromFile(file);
                stop = true;
            } catch (FileNotFoundException e) {
                System.out.println("File was not found.");
            } catch (InputMismatchException e) {
                System.out.println("Path has to be a string");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void changePriceTrip(Scanner sc) {
        System.out.println("Change price.");
        String company;
        SeatClass seatClass;
        String travelID;
        double price;
        sc.nextLine();
        boolean stop = false;
        while(!stop) {
            try {
                System.out.println("Enter name of the company(ex: DELTA):");
                company = sc.nextLine();
                System.out.println("Enter travel number(ex: DL546):");
                travelID = sc.nextLine();

                seatClass = getSeatClass(sc);

                price = getPrice(sc);

                manager.changeSpotPriceBySection(company,travelID, seatClass, price);
                stop = true;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void findTrips(Scanner sc) {
        manager.findAvailableTravels("","",SeatClass.ECONOMY,0,0,0);
    }

    public void changePriceTravel(Scanner sc) {
        System.out.println("Change price(origin, destination).");
        String company;
        String origin;
        String destination;
        SeatClass seatClass;
        double price;
        sc.nextLine();
        boolean stop = false;
        while(!stop) {
            try {
                System.out.println("Enter name of the company(ex: DELTA):");
                company = sc.nextLine();
                System.out.println("Enter origin(ex: LAX):");
                origin = sc.nextLine();
                System.out.println("Enter destination(ex: LAX):");
                destination = sc.nextLine();

                seatClass = getSeatClass(sc);

                price = getPrice(sc);

                manager.changeSpotPriceByOriginDestination(company, seatClass, origin, destination, price);
                stop = true;
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void bookSpot(Scanner sc) {
        manager.bookSpot("","",SeatClass.ECONOMY, 10, 'A');
    }

    public void bookSpotPreference(Scanner sc) {}

    public void saveToFile(Scanner sc) {}

    private SeatClass getSeatClass(Scanner sc) {

        int seatClassNum;
        boolean seatClassCheck = false;
        SeatClass seatClass = null;

        System.out.println("Choose seat class:\n" +
                "1. Business\n" +
                "2. First\n" +
                "3. Economy");
        while(!seatClassCheck) {
            seatClassNum = sc.nextInt();
            if (seatClassNum == 1) {
                seatClass = SeatClass.BUSINESS;
                seatClassCheck = true;
            }
            else if(seatClassNum == 2) {
                seatClass = SeatClass.FIRST;
                seatClassCheck = true;
            }
            else if(seatClassNum == 3) {
                seatClass = SeatClass.ECONOMY;
                seatClassCheck = true;
            }
            else
                System.out.println("Incorrect number.");

        }

        return seatClass;
    }

    private double getPrice(Scanner sc) {
        boolean priceCorrect = false;
        double price = -1;
        System.out.println("Enter new price(ex: 200):");
        while(!priceCorrect) {
            price = sc.nextDouble();
            if(price > 0 && price < 20000)
                priceCorrect = true;
            else
                System.out.println("Price has to be in range(1, 20,000)");

        }
        return price;
    }
}

