package ACTBS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {
    Section testSectionMedium = new Section(2, SeatLayout.MEDIUM, SeatClass.BUSINESS, 100);
    Section testSectionWide = new Section(2, SeatLayout.WIDE, SeatClass.BUSINESS, 100);
    Section testSectionSmall = new Section(2, SeatLayout.SMALL, SeatClass.BUSINESS, 100);
    @Test
    void testGetSeatClass() {
        assertEquals("BUSINESS", testSectionMedium.getSeatClass());
    }

    @Test
    void testHasAvailableSeats() {
        assertEquals(true, testSectionMedium.hasAvailableSpots());
    }

    @Test
    void testBookSeat() {
        assertEquals(true, testSectionMedium.bookSpot(1 ,3));
        assertEquals(false, testSectionMedium.bookSpot(1 ,3));
    }

    @Test
    void testToString() {
        String sectionMedium =
                "\tBUSINESS class:\n" +
                        "\t1A 1B 1C 1D\n" +
                        "\t2A 2B 2C 2D\n";

        assertEquals(sectionMedium, testSectionMedium.toString());

        String sectionWide =
                "\tBUSINESS class:\n" +
                        "\t1A 1B 1C 1D 1E 1F 1G 1H 1I 1J\n" +
                        "\t2A 2B 2C 2D 2E 2F 2G 2H 2I 2J\n";

        assertEquals(sectionWide, testSectionWide.toString());


        String sectionSmall =
                "\tBUSINESS class:\n" +
                        "\t1A 1B 1C\n" +
                        "\t2A 2B 2C\n";

        assertEquals(sectionSmall, testSectionSmall.toString());
    }
}