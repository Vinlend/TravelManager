package ACTBS;

import ACTBS.SystemExceptions.*;

public class Seat {
    private String ID;
    private boolean booked;

    public Seat(int row, int column) {
        if(row < 1 || row > 100)
            throw new RowOutOfBoundsException("Rows out of range(1,100):Seat");
        if(column < 1 || column > 10)
            throw new ColumnOutOfBoundsException("Columns out of range(1,10):Seat");

        this.ID = row + String.valueOf((char)(64+column));
        booked = false;
    }

    public String getID() {
        return ID;
    }

    public boolean isBooked() {
        return booked;
    }

    public boolean book() {
        if (!booked) {
            booked = true;
            return true;
        } else {
            return false;
        }
    }
}
