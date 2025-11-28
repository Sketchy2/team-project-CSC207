package use_case.save;

import data_access.InMemoryOutfitsGateway;
import entities.Outfit;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SaveOutfitInteractor.
 *
 * This class tests ALL logic branches:
 *  1. Name is empty → failure
 *  2. Items list empty → failure
 *  3. Duplicate exists and overwrite=false → failure
 *  4. Save works when no duplicate exists → success
 *  5. Overwriting an existing outfit → success
 *
 * We use:
 *  - a real InMemoryOutfitsGateway (simple, deterministic)
 *  - a TestPresenter (test double capturing presenter output)
 */
class SaveOutfitInteractorTest {

    /**
     * Simple presenter that records last success/fail calls.
     */
    private static class TestPresenter implements SaveOutfitOutputBoundary {

        SaveOutfitOutputData lastSuccessData;
        String lastErrorMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        @Override
        public void prepareSuccessView(SaveOutfitOutputData data) {
            this.lastSuccessData = data;
            this.successCalled = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.lastErrorMessage = errorMessage;
            this.failCalled = true;
        }
    }

    @Test
    void executeFailsWhenNameIsBlank() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();
        TestPresenter presenter = new TestPresenter();
        SaveOutfitInteractor interactor = new SaveOutfitInteractor(gateway, presenter);

        // Act: blank name triggers first validation failure branch.
        SaveOutfitInputData input = new SaveOutfitInputData(
                "   ",
                List.of("coat"),
                "cold",
                "Toronto",
                false
        );
        interactor.execute(input);

        // Assert
        assertTrue(presenter.failCalled);
        assertEquals("Name cannot be empty.", presenter.lastErrorMessage);
        assertTrue(gateway.getAll().isEmpty());
    }

    @Test
    void executeFailsWhenItemsListIsEmpty() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();
        TestPresenter presenter = new TestPresenter();
        SaveOutfitInteractor interactor = new SaveOutfitInteractor(gateway, presenter);

        // Act: triggers second validation branch.
        SaveOutfitInputData input = new SaveOutfitInputData(
                "Valid Name",
                List.of(),
                "cold",
                "Toronto",
                false
        );
        interactor.execute(input);

        // Assert
        assertTrue(presenter.failCalled);
        assertEquals("Outfit must include at least one item.", presenter.lastErrorMessage);
    }

    @Test
    void executeFailsWhenDuplicateExistsAndOverwriteIsFalse() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();

        // Seed existing outfit with matching identifiers
        gateway.save(new Outfit("Fit1", List.of("coat"), "cold", "Toronto"));

        TestPresenter presenter = new TestPresenter();
        SaveOutfitInteractor interactor = new SaveOutfitInteractor(gateway, presenter);

        // Act: duplicate exists + overwrite=false triggers duplicate branch
        SaveOutfitInputData input = new SaveOutfitInputData(
                "Fit1",
                List.of("coat", "hat"),
                "cold",
                "Toronto",
                false
        );
        interactor.execute(input);

        // Assert
        assertTrue(presenter.failCalled);
        assertEquals("An identical outfit already exists.", presenter.lastErrorMessage);
        assertEquals(1, gateway.getAll().size());
    }

    @Test
    void executeSavesNewOutfitWhenNotExisting() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();
        TestPresenter presenter = new TestPresenter();
        SaveOutfitInteractor interactor = new SaveOutfitInteractor(gateway, presenter);

        // Act: normal save branch
        SaveOutfitInputData input = new SaveOutfitInputData(
                "New Fit",
                List.of("coat", "boots"),
                "cold",
                "Toronto",
                false
        );
        interactor.execute(input);

        // Assert success path
        assertTrue(presenter.successCalled);
        assertNotNull(presenter.lastSuccessData);
        assertEquals("Outfit saved successfully!", presenter.lastSuccessData.getMessage());

        // Verify gateway was updated
        assertEquals(1, gateway.getAll().size());
    }

    @Test
    void executeOverwritesExistingWhenOverwriteIsTrue() {
        // Arrange
        InMemoryOutfitsGateway gateway = new InMemoryOutfitsGateway();

        // Existing outfit
        gateway.save(new Outfit("Fit1", List.of("coat"), "cold", "Toronto"));

        TestPresenter presenter = new TestPresenter();
        SaveOutfitInteractor interactor = new SaveOutfitInteractor(gateway, presenter);

        // Act: overwrite=true allows replacement
        SaveOutfitInputData input = new SaveOutfitInputData(
                "Fit1",
                List.of("coat", "hat"),
                "cold",
                "Toronto",
                true
        );
        interactor.execute(input);

        // Assert
        assertTrue(presenter.successCalled);
        List<Outfit> stored = gateway.getAll();
        assertEquals(List.of("coat", "hat"), stored.get(0).getItems());
    }
}
