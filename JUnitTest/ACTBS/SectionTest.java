package ACTBS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {
    Section testSection = new Section(2, 5, SeatClass.business);
    @Test
    void testGetSeatClass() {
        assertEquals("business", testSection.getSeatClass());
    }

    @Test
    void testHasAvailableSeats() {
        assertEquals(true, testSection.hasAvailableSeats());
    }

    @Test
    void testBookSeat() {
        assertEquals(true, testSection.bookSeat(1 ,3));
        assertEquals(false, testSection.bookSeat(1 ,3));
    }

    @Test
    void testToString() {
        String section =
                "\tbusiness class:\n" +
                        "\t1A 1B 1C 1D 1E\n" +
                        "\t2A 2B 2C 2D 2E\n";

        assertEquals(section, testSection.toString());
    }
}