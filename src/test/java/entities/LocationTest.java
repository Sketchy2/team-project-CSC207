package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void constructorAndGettersWork() {
        Location loc = new Location("Paris", "FR", 48.8566, 2.3522);

        assertEquals("Paris", loc.getName());
        assertEquals("FR", loc.getCountryCode());
        assertEquals(48.8566, loc.getLatitude(), 1e-6);
        assertEquals(2.3522, loc.getLongitude(), 1e-6);
    }
}

