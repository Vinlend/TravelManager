package ACTBS;

import ACTBS.SystemExceptions.ColumnOutOfBoundsException;
import ACTBS.SystemExceptions.RowOutOfBoundsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SeatTest {
    @Test
    void testRowOutOfBoundaries() {
        assertThrows(RowOutOfBoundsException.class, () -> new Seat(-1, 1));
        assertThrows(RowOutOfBoundsException.class, () -> new Seat(101, 1));
    }

    @Test
    void testColumnOutOfBoundaries() {
        assertThrows(ColumnOutOfBoundsException.class, () -> new Seat(1, -1));
        assertThrows(ColumnOutOfBoundsException.class, () -> new Seat(1, 11));
    }

    @Test
    void testGetID() {
        assertEquals("1A", new Seat(1, 1).getID());
        assertEquals("100J", new Seat(100, 10).getID());
    }

    @Test
    void testIsBooked() {
        assertEquals( false, new Seat(1, 1).isBooked());
        Seat bookSeat = new Seat(1,1);
        bookSeat.book();
        assertEquals(true, bookSeat.isBooked());
    }

    @Test
    void testBook() {
        Seat bookSeat = new Seat(1,1);
        assertEquals( true, bookSeat.book());
        assertEquals(false, bookSeat.book());
    }

}