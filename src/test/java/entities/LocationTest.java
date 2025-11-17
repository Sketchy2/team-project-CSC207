package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void validConstructorTrimsNameAndStoresFields() {
        Location loc = new Location("  Toronto  ", "CA", 43.7, -79.4);

        assertEquals("Toronto", loc.getName());
        assertEquals("CA", loc.getCountryCode());
        assertEquals(43.7, loc.getLatitude());
        assertEquals(-79.4, loc.getLongitude());
    }

    @Test
    void constructorRejectsNullName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location(null, "CA", 43.7, -79.4));
    }

    @Test
    void constructorRejectsEmptyOrBlankName() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location("", "CA", 43.7, -79.4));

        assertThrows(IllegalArgumentException.class, () ->
                new Location("   ", "CA", 43.7, -79.4));
    }

    @Test
    void constructorRejectsLatitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location("Toronto", "CA", -91.0, -79.4));

        assertThrows(IllegalArgumentException.class, () ->
                new Location("Toronto", "CA", 91.0, -79.4));
    }

    @Test
    void constructorRejectsLongitudeOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location("Toronto", "CA", 43.7, -181.0));

        assertThrows(IllegalArgumentException.class, () ->
                new Location("Toronto", "CA", 43.7, 181.0));
    }

    @Test
    void constructorRejectsNaNLatitudeOrLongitude() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location("Toronto", "CA", Double.NaN, -79.4));

        assertThrows(IllegalArgumentException.class, () ->
                new Location("Toronto", "CA", 43.7, Double.NaN));
    }

    @Test
    void equalsAndHashCodeWorkForSameValues() {
        Location loc1 = new Location("Toronto", "CA", 43.7, -79.4);
        Location loc2 = new Location("Toronto", "CA", 43.7, -79.4);

        assertEquals(loc1, loc2);
        assertEquals(loc1.hashCode(), loc2.hashCode());
    }
}

