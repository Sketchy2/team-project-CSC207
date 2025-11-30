package use_case.save;

import data_access.OutfitsGateway;
import entities.Outfit;

import java.util.List;

/**
 * Contains the business logic for saving an outfit.
 * Performs validation, duplicate checking, normalization,
 * and delegates persistence to the data access gateway.
 */
public class SaveOutfitInteractor implements SaveOutfitInputBoundary {

    private final OutfitsGateway gateway;
    private final SaveOutfitOutputBoundary presenter;

    public SaveOutfitInteractor(OutfitsGateway gateway,
                                SaveOutfitOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(SaveOutfitInputData data) {

        // -----------------------------
        // 1. Normalize input fields
        // -----------------------------
        String name = data.getName() == null ? "" : data.getName().trim();
        String profile = data.getWeatherProfile() == null ?
                "" : data.getWeatherProfile().trim();
        String location = data.getLocation() == null ?
                "" : data.getLocation().trim();

        // -----------------------------
        // 2. Validate fields
        // -----------------------------
        if (name.isBlank()) {
            presenter.prepareFailView("Name cannot be empty.");
            return;
        }

        if (data.getItems() == null || data.getItems().isEmpty()) {
            presenter.prepareFailView("Outfit must include at least one item.");
            return;
        }

        // -----------------------------
        // 3. Duplicate detection
        // -----------------------------
        boolean exists = gateway.exists(name, profile, location);

        if (exists && !data.isOverwrite()) {
            presenter.prepareFailView("An identical outfit already exists.");
            return;
        }

        // -----------------------------
        // 4. Save entity
        // -----------------------------
        Outfit outfit = new Outfit(
                name,
                data.getItems(),
                profile,
                location
        );

        gateway.save(outfit);

        // -----------------------------
        // 5. Prepare response for UI
        // -----------------------------
        List<Outfit> all = gateway.getAll();

        SaveOutfitOutputData output = new SaveOutfitOutputData(
                all,
                "Outfit saved successfully!"
        );

        presenter.prepareSuccessView(output);
    }
}
