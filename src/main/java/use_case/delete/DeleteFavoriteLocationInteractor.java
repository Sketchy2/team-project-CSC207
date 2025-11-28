package use_case.delete;

import data_access.FavoriteLocationsGateway;
import java.util.List;

/** Interactor for deleting a favorite location.
 * <p>
 * This use case handles validation and check whether location exists,
 * performs deletion through {@link FavoriteLocationsGateway},
 * prepares either a success or failure view through the presenter.
 */
public class DeleteFavoriteLocationInteractor implements DeleteFavoriteLocationInputBoundary {
    private final FavoriteLocationsGateway gateway;
    private final DeleteFavoriteLocationOutputBoundary presenter;

    /**
     * Creates a new interactor for deleting favorite locations.
     * @param gateway the data access interface for favorite locations
     * @param presenter the presenter that formats the output of the view
     */
    public DeleteFavoriteLocationInteractor(FavoriteLocationsGateway gateway,
                                            DeleteFavoriteLocationOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    /**
     * Execute the delete-favorite-location use case
     * @param inputData the input data containing the name to delete
     */
    @Override
    public void execute(DeleteFavoriteLocationInputData inputData) {
        String rawCityName = inputData.getCityName();

        //Basic validation
        if (rawCityName == null || rawCityName.trim().isEmpty()) {
            presenter.prepareFailView("Please check your city name and try again.");
            return;
        }

        //Location not found to delete
        String cityName = rawCityName.trim();
        if (!gateway.existsByName(cityName)) {
            presenter.prepareFailView("Location not found in favorites: " + cityName);
            return;
        }

        //Succeesfully deleted
        gateway.delete(cityName);

        List<String> updatedFavorites = gateway.getFavorites();

        DeleteFavoriteLocationOutputData output = new DeleteFavoriteLocationOutputData(cityName,
                updatedFavorites);

        presenter.prepareSuccessView(output);
    }
}
