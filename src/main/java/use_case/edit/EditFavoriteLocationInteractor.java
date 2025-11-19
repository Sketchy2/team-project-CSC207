package use_case.edit;

import data_access.FavoriteLocationsGateway;

import java.util.List;

/**
 * Business logic for renaming a favourite location.
 */
public class EditFavoriteLocationInteractor implements EditFavoriteLocationInputBoundary {

    private final FavoriteLocationsGateway gateway;
    private final EditFavoriteLocationOutputBoundary presenter;

    public EditFavoriteLocationInteractor(FavoriteLocationsGateway gateway,
                                          EditFavoriteLocationOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditFavoriteLocationInputData data) {
        String oldName = data.getOldName();
        String newName = data.getNewName();

        // Basic validation
        if (oldName == null || oldName.isBlank()) {
            presenter.prepareFailView("Original city name is missing.");
            return;
        }
        if (newName == null || newName.isBlank()) {
            presenter.prepareFailView("City name cannot be empty.");
            return;
        }

        // Must exist
        if (!gateway.existsByName(oldName)) {
            presenter.prepareFailView("Original city not found in favourites.");
            return;
        }

        // If changing to a different name, ensure it isn't already in favourites
        if (!oldName.equals(newName) && gateway.existsByName(newName)) {
            presenter.prepareFailView("That city is already saved as a favourite.");
            return;
        }

        // If names are identical, nothing to change – just return current list
        if (oldName.equals(newName)) {
            List<String> favorites = gateway.getFavorites();
            EditFavoriteLocationOutputData output =
                    new EditFavoriteLocationOutputData(favorites, "No changes made.");
            presenter.prepareSuccessView(output);
            return;
        }

        // Perform rename: delete old, save new
        gateway.delete(oldName);
        boolean ok = gateway.saveFavorites(newName);  // or gateway.save(newName) if that's the correct one

        if (!ok) {
            presenter.prepareFailView("Unable to save updated city. Please try again.");
            return;
        }

        List<String> favorites = gateway.getFavorites();
        EditFavoriteLocationOutputData output =
                new EditFavoriteLocationOutputData(favorites, "City renamed successfully.");
        presenter.prepareSuccessView(output);
    }
}
