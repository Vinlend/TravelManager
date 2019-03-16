package ACTBS;

import ACTBS.SystemExceptions.ColumnOutOfBoundsException;
import ACTBS.SystemExceptions.RowOutOfBoundsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpotTest {
    @Test
    void testRowOutOfBoundaries() {
        assertThrows(RowOutOfBoundsException.class, () -> new Spot(-1, 1));
        assertThrows(RowOutOfBoundsException.class, () -> new Spot(101, 1));
    }

    @Test
    void testColumnOutOfBoundaries() {
        assertThrows(ColumnOutOfBoundsException.class, () -> new Spot(1, -1));
        assertThrows(ColumnOutOfBoundsException.class, () -> new Spot(1, 11));
    }

    @Test
    void testGetID() {
        assertEquals("1A", new Spot(1, 1).getID());
        assertEquals("100J", new Spot(100, 10).getID());
    }

    @Test
    void testIsBooked() {
        assertEquals( false, new Spot(1, 1).isBooked());
        Spot bookSpot = new Spot(1,1);
        bookSpot.book();
        assertEquals(true, bookSpot.isBooked());
    }

    @Test
    void testBook() {
        Spot bookSpot = new Spot(1,1);
        assertEquals( true, bookSpot.book());
        assertEquals(false, bookSpot.book());
    }

}