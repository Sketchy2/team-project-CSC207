package use_case.save;

import entities.Outfit;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that the Output Data object passed Interactor → Presenter
 * correctly exposes its stored fields.
 */
class SaveOutfitOutputDataTest {

    @Test
    void gettersReturnProvidedValues() {
        // Arrange
        Outfit outfit = new Outfit("Fit1", List.of("coat"), "cold", "Toronto");
        List<Outfit> outfits = List.of(outfit);

        // Act
        SaveOutfitOutputData data = new SaveOutfitOutputData(outfits, "Success!");

        // Assert
        assertEquals(outfits, data.getOutfits());
        assertEquals("Success!", data.getMessage());
    }
}
