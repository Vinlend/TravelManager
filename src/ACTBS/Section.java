package ACTBS;

import ACTBS.SystemExceptions.*;

public class Section {
    private SeatClass seatClass;
    private Spot spots[][];

    private double price;


    public Section(int rows, int cols, SeatClass seatClass) {
        if (rows < 1 || rows > 100)
            throw new RowOutOfBoundsException("Rows out of range(1,100):Section");
        if (cols < 1 || cols > 10)
            throw new ColumnOutOfBoundsException("Columns out of range(1,10):Section");

        this.spots = new Spot[rows][cols];

        initializeSpots();

        this.seatClass = seatClass;
    }

    public String getSeatClass() {
        return seatClass.name();
    }


    public double getPrice() {
        return this.price;
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


    private void initializeSpots() {

        for (int row = 0; row < spots.length; row++) {
            for (int col = 0; col < spots[0].length; col++) {
                spots[row][col] = new Spot(row+1, col+1);
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
    
    public String getAvailableSports() {
        String section = String.format("%s class:\n", getSeatClass() );

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
    
}
