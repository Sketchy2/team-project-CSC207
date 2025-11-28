package entities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Outfit entity.
 * <p>
 * Entities must be simple, immutable data holders.
 * This test ensures that:
 *  - constructor stores values correctly
 *  - getters return exactly what was provided
 */
class OutfitTest {

    @Test
    void constructorAndGettersReturnProvidedValues() {
        // Arrange: create some test clothing items
        List<String> items = List.of("coat", "scarf");

        Outfit outfit = new Outfit("Snow Day Fit", items,
                "cold & windy", "Toronto");

        // Assert: verify all getters return the correct values
        assertEquals("Snow Day Fit", outfit.getName());
        assertEquals(items, outfit.getItems());
        assertEquals("cold & windy", outfit.getWeatherProfile());
        assertEquals("Toronto", outfit.getLocation());
    }
}
