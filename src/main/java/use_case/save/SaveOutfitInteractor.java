package use_case.save;

import data_access.OutfitsGateway;
import entities.Outfit;

import java.util.List;

/**
 * Contains the business logic for saving an outfit.
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

        // 1. Validate
        if (data.getName() == null || data.getName().isBlank()) {
            presenter.prepareFailView("Name cannot be empty.");
            return;
        }
        if (data.getItems().isEmpty()) {
            presenter.prepareFailView("Outfit must include at least one item.");
            return;
        }

        // 2. Check duplicates
        boolean exists = gateway.exists(
                data.getName(),
                data.getWeatherProfile(),
                data.getLocation()
        );

        if (exists && !data.isOverwrite()) {
            presenter.prepareFailView("An identical outfit already exists.");
            return;
        }

        // 3. Save entity
        Outfit outfit = new Outfit(
                data.getName(),
                data.getItems(),
                data.getWeatherProfile(),
                data.getLocation()
        );

        gateway.save(outfit);

        // 4. Prepare output
        List<Outfit> all = gateway.getAll();
        SaveOutfitOutputData output = new SaveOutfitOutputData(all, "Outfit saved successfully!");
        presenter.prepareSuccessView(output);
    }
}
