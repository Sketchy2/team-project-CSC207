package data_access;

import entities.Outfit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the in-memory implementation of the OutfitsGateway.
 *
 * Although data-access tests are not required for Clean Architecture,
 * they help verify the correctness of your gateway logic and improve coverage.
 */
class InMemoryOutfitsGatewayTest {

    @Test
    void saveAddsNewOutfit() {
        // Arrange: empty gateway
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();

        // Act: save one outfit
        Outfit outfit = new Outfit("Fit1", List.of("coat"), "cold", "Toronto");
        gateway.save(outfit);

        // Assert: verify it was stored
        List<Outfit> all = gateway.getAll();
        assertEquals(1, all.size());
        assertEquals("Fit1", all.get(0).getName());
    }

    @Test
    void saveOverwritesIdenticalOutfit() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();

        // First version
        Outfit outfit1 = new Outfit("Fit1", List.of("coat"), "cold", "Toronto");

        // Updated version with extra item
        Outfit outfit2 = new Outfit("Fit1", List.of("coat", "hat"), "cold", "Toronto");

        // Act: save both
        gateway.save(outfit1);
        gateway.save(outfit2); // overwrites existing

        // Assert: only second outfit persists
        List<Outfit> all = gateway.getAll();
        assertEquals(1, all.size());
        assertEquals(List.of("coat", "hat"), all.get(0).getItems());
    }

    @Test
    void existsChecksNameProfileLocation() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();
        gateway.save(new Outfit("Fit1", List.of("coat"), "cold", "Toronto"));

        // Assert: matching values → true
        assertTrue(gateway.exists("Fit1", "cold", "Toronto"));

        // Non-matching → false
        assertFalse(gateway.exists("Fit1", "warm", "Toronto"));
        assertFalse(gateway.exists("Other", "cold", "Toronto"));
    }

    @Test
    void deleteRemovesMatchingOutfit() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();
        gateway.save(new Outfit("Fit1", List.of("coat"), "cold", "Toronto"));

        // Act
        gateway.delete("Fit1", "cold", "Toronto");

        // Assert
        assertFalse(gateway.exists("Fit1", "cold", "Toronto"));
        assertTrue(gateway.getAll().isEmpty());
    }

    @Test
    void getAllReturnsDefensiveCopy() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();
        gateway.save(new Outfit("Fit1", List.of("coat"), "cold", "Toronto"));

        // Act: modify returned list
        List<Outfit> list = gateway.getAll();
        list.clear();

        // Assert: internal list was not modified
        assertEquals(1, gateway.getAll().size());
    }
}
