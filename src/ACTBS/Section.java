package ACTBS;

import ACTBS.SystemExceptions.*;

public class Section {
    private SeatClass seatClass;
    private Spot spots[][];

    private double price;


    public Section(int rows, SeatLayout seatLayout, SeatClass seatClass, double price) {
        if (rows < 1 || rows > 100)
            throw new RowOutOfBoundsException("Rows out of range(1,100):Section");
        if(price < 0)
            throw new PriceIsNegativeException("Price cannot be negative");

        this.spots = new Spot[rows][seatLayout.getColumn()];
        initializeSpots(seatLayout);

        this.seatClass = seatClass;
        this.price = price;
    }

    public String getSeatClass() {
        return seatClass.name();
    }


    public double getPrice() {
        return this.price;
    }

    public boolean setPrice(double price) {
        boolean priceChanged;
        if (price < 0)
            priceChanged = false;
        else {
            this.price = price;
            priceChanged = true;
        }
        return priceChanged;
    }

    public boolean hasAvailableSpots() {

        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++)
                if (spots[row][col].isBooked() == false)
                    return true;
        }
        return false;
    }

    public boolean bookSpot(int row, int col) {

        if (spots[row-1][col].isBooked() == false) {
            spots[row-1][col].book();
            return true;
        }
        return false;
    }

    public boolean bookByPreference(Position position) {
        return false;
    }

    private void initializeSpots(SeatLayout layout) {

        switch (layout){
            case WIDE:
                initializeWide();
                break;
            case SMALL:
                initializeSmall();
                break;
            case MEDIUM:
                initializeMedium();
                break;
        }
    }


    private void initializeWide(){
        Position position;
        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++) {
                if(col == 0 || col == spots[0].length-1)
                    position = Position.WINDOW;
                else if(col == 2 || col == 3 || col == 6 || col == 7)
                    position = Position.AISLE;
                else
                    position = Position.NONE;
                spots[row][col] = new Spot(row+1, col+1, position);
            }
        }
    }
    private void initializeMedium() {
        Position position;
        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++) {
                if(col == 0 || col == spots[0].length-1)
                    position = Position.WINDOW;
                else if(col == 1 || col == 2)
                    position = Position.AISLE;
                else
                    position = Position.NONE;
                spots[row][col] = new Spot(row+1, col+1, position);
            }
        }
    }
    private void initializeSmall() {
        Position position;
        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++) {
                if(col == 0)
                    position = Position.BOTH;
                else if(col == spots[0].length-1)
                    position = Position.WINDOW;
                else if(col == 1)
                    position = Position.AISLE;
                else
                    position = Position.NONE;
                spots[row][col] = new Spot(row+1, col+1, position);
            }
        }
    }

    @Override
    public String toString() {
        String section = String.format("\t%s class:\n", getSeatClass() );

        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++) {
                section += ((col == 0) ? "\t" : "") + spots[row][col].getID() + ((col == spots[0].length-1) ? "" : " ");
            }
            section += "\n";
        }

        return section;
    }
    
    public String getAvailableSpots() {
        String section = String.format("%s class: (Price: " + this.getPrice() + ")\n", getSeatClass() );

        for (int row = 0; row < spots.length; row++) {
        	section += "\t";
            for (int col = 0; col < spots[0].length; col++) {
            	section += String.format("%2s ", (spots[row][col].isBooked()) ? "--" : spots[row][col].getID());

                //section += ((col == 0) ? "\t" : "") + seats[row][col].getID() + ((col == seats[0].length-1) ? "" : " ");

            }
            section += "\n";
        }

        return section;
    }

    private void printBooked() {
        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++)
                System.out.print(spots[row][col].isBooked() + " ");
            System.out.println("\n");
        }

    }

    public void printPositions() {
        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++)
                System.out.print(spots[row][col].getPosition() + " ");
            System.out.println("\n");
        }

    }
}
