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
        try {
            sc.nextLine();
            System.out.println("Type the path to the file:");
            String filePath = sc.nextLine();
            Scanner file = new Scanner(new File(filePath));

            //manager.loadFromFile(file);
        }
        catch(FileNotFoundException e) {
            System.out.println("File was not found.");
            loadFromFile(sc);
        }
        catch(InputMismatchException e) {
            System.out.println("Path has to be a string");
        }
        catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changePriceTrip(Scanner sc) {


        manager.changeSpotPriceBySection("Delta","DEL134", SeatClass.BUSINESS,200);
    }

    public void findTrips(Scanner sc) {}

    public void changePriceTravel(Scanner sc) {
       manager.changeSpotPriceByOriginDestination("",SeatClass.ECONOMY,"","",100);
    }

    public void bookSpot(Scanner sc) {}

    public void bookSpotPreference(Scanner sc) {}

    public void saveToFile(Scanner sc) {}
}

