package ACTBS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {
    Section testSection = new Section(2, 5, SeatClass.BUSINESS, 100);
    @Test
    void testGetSeatClass() {
        assertEquals("BUSINESS", testSection.getSeatClass());
    }

    @Test
    void testHasAvailableSeats() {
        assertEquals(true, testSection.hasAvailableSpots());
    }

    @Test
    void testBookSeat() {
        assertEquals(true, testSection.bookSpot(1 ,3));
        assertEquals(false, testSection.bookSpot(1 ,3));
    }

    @Test
    void testToString() {
        String section =
                "\tBUSINESS class:\n" +
                        "\t1A 1B 1C 1D 1E\n" +
                        "\t2A 2B 2C 2D 2E\n";

        assertEquals(section, testSection.toString());
    }
}