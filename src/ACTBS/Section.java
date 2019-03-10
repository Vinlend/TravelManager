package ACTBS;

import ACTBS.SystemExceptions.*;

public class Section {
    private SeatClass seatClass;
    private Seat seats[][];

    public Section(int rows, int cols, SeatClass seatClass) {
        if (rows < 1 || rows > 100)
            throw new RowOutOfBoundsException("Rows out of range(1,100):Section");
        if (cols < 1 || cols > 10)
            throw new ColumnOutOfBoundsException("Columns out of range(1,10):Section");

        this.seats = new Seat[rows][cols];
        initializeSeats();
        this.seatClass = seatClass;
    }

    public String getSeatClass() {
        return seatClass.name();
    }

    public boolean hasAvailableSeats() {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[0].length; col++)
                if (seats[row][col].isBooked() == false)
                    return true;
        }
        return false;
    }

    public boolean bookSeat(int row, int col) {
        if (seats[row-1][col].isBooked() == false) {
            seats[row-1][col].book();
            return true;
        }
        return false;
    }

    private void initializeSeats() {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[0].length; col++) {
                seats[row][col] = new Seat(row+1, col+1);
            }
        }
    }

    @Override
    public String toString() {
        String section = String.format("\t%s class:\n", getSeatClass() );

        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[0].length; col++) {
                section += ((col == 0) ? "\t" : "") + seats[row][col].getID() + ((col == seats[0].length-1) ? "" : " ");
            }
            section += "\n";
        }

        return section;
    }
    
    public String getAvailableSeats() {
        String section = String.format("%s class:\n", getSeatClass() );

        for (int row = 0; row < seats.length; row++) {
        	section += "\t";
            for (int col = 0; col < seats[0].length; col++) {
            	section += String.format("%2s ", (seats[row][col].isBooked()) ? "--" : seats[row][col].getID());
                //section += ((col == 0) ? "\t" : "") + seats[row][col].getID() + ((col == seats[0].length-1) ? "" : " ");
            }
            section += "\n";
        }

        return section;
    }

    private void printBooked() {
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[0].length; col++)
                System.out.print(seats[row][col].isBooked() + " ");
            System.out.println("\n");
        }

    }
    
}
