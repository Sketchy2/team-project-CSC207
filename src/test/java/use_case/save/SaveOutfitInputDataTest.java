package use_case.save;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies that the Input Data object correctly stores and returns
 * the fields passed from the Controller → Interactor.
 */
class SaveOutfitInputDataTest {

    @Test
    void gettersReturnProvidedValues() {
        List<String> items = List.of("coat", "boots");

        // Act: create input DTO
        SaveOutfitInputData data = new SaveOutfitInputData(
                "Snow Day Fit",
                items,
                "cold & windy",
                "Toronto",
                true
        );

        assertEquals("Snow Day Fit", data.getName());
        assertEquals(items, data.getItems());
        assertEquals("cold & windy", data.getWeatherProfile());
        assertEquals("Toronto", data.getLocation());
        assertTrue(data.isOverwrite());
    }
}
