package use_case;

import data_access.InMemoryOutfitsGateway;
import entities.Outfit;
import use_case.delete.DeleteOutfitInteractor;
import use_case.delete.DeleteOutfitInputData;
import use_case.delete.DeleteOutfitOutputBoundary;
import use_case.delete.DeleteOutfitOutputData;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DeleteOutfitInteractor}.
 * Verifies that:
 *  - an existing outfit is deleted successfully
 *  - attempting to delete a non-existing outfit triggers failure
 */
public class DeleteOutfitInteractorTest {

    /**
     * Tests that deleting an existing outfit calls the success presenter
     * and returns the updated outfit list.
     */
    @Test
    public void testDeleteExistingOutfit() {

        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();

        // Pre-populate gateway (simulate saved data)
        Outfit outfit = new Outfit(
                "Winter Fit",
                Arrays.asList("Jacket", "Boots"),
                "Cold",
                "Toronto"
        );
        gateway.save(outfit);

        // Fake presenter to capture output
        DeleteOutfitOutputBoundary presenter = new DeleteOutfitOutputBoundary() {

            @Override
            public void prepareSuccessView(DeleteOutfitOutputData data) {
                // After deletion, list should be empty
                assertTrue(data.getOutfits().isEmpty());
                assertEquals("Outfit deleted.", data.getMessage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not fail when outfit exists.");
            }
        };

        DeleteOutfitInteractor interactor = new DeleteOutfitInteractor(gateway, presenter);

        DeleteOutfitInputData input =
                new DeleteOutfitInputData("Winter Fit", "Cold", "Toronto");

        interactor.execute(input);
    }

    /**
     * Tests that attempting to delete a non-existing outfit
     * calls the failure presenter.
     */
    @Test
    public void testDeleteNonExistingOutfit() {

        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();

        DeleteOutfitOutputBoundary presenter = new DeleteOutfitOutputBoundary() {

            @Override
            public void prepareSuccessView(DeleteOutfitOutputData data) {
                fail("Should not succeed when outfit does NOT exist.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Outfit not found.", errorMessage);
            }
        };

        DeleteOutfitInteractor interactor = new DeleteOutfitInteractor(gateway, presenter);

        DeleteOutfitInputData input =
                new DeleteOutfitInputData("NonExistent", "Sunny", "Paris");

        interactor.execute(input);
    }
}
