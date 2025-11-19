package use_case.view;

import data_access.OutfitsGateway;
import data_access.FavoriteLocationsGateway;
import entities.Outfit;

import java.util.List;

/**
 * Business logic for viewing saved outfits + favourite locations.
 */
public class ViewSavedItemsInteractor implements ViewSavedItemsInputBoundary {

    private final OutfitsGateway outfitsGateway;
    private final FavoriteLocationsGateway locationsGateway;
    private final ViewSavedItemsOutputBoundary presenter;

    public ViewSavedItemsInteractor(OutfitsGateway outfitsGateway,
                                    FavoriteLocationsGateway locationsGateway,
                                    ViewSavedItemsOutputBoundary presenter) {
        this.outfitsGateway = outfitsGateway;
        this.locationsGateway = locationsGateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewSavedItemsInputData inputData) {
        try {
            List<Outfit> outfits = outfitsGateway.getAll();
            List<String> favorites = locationsGateway.getFavorites();

            if (outfits.isEmpty() && favorites.isEmpty()) {
                presenter.prepareFailView("No saved items found.");
                return;
            }

            ViewSavedItemsOutputData output =
                    new ViewSavedItemsOutputData(outfits, favorites, "Saved items loaded.");
            presenter.prepareSuccessView(output);

        } catch (Exception e) {
            presenter.prepareFailView("Unable to load saved items.");
        }
    }
}
