package use_case.save;

import data_access.FavoriteLocationsGateway;

/** Interactor for saving a favorite location.
 * <p>
 * This use case handles validation and check for duplicates of location,
 * saves a favorite location through {@link FavoriteLocationsGateway},
 * prepares either a success or failure view through the presenter.
 */
public class SaveFavoriteLocationInteractor implements SaveFavoriteLocationInputBoundary {
    private final FavoriteLocationsGateway gateway;
    private final SaveFavoriteLocationOutputBoundary presenter;

    /**
     * Creates a new interactor for saving favorite locations.
     * @param gateway the data access interface used to store and retrieve favorite locations
     * @param presenter the presenter that formats the output of the view
     */
    public SaveFavoriteLocationInteractor(FavoriteLocationsGateway gateway,
                                          SaveFavoriteLocationOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    /**
     * Execute the save-favorite-location use case
     * @param inputData the input data containing the name to delete
     */
    @Override
    public void execute(SaveFavoriteLocationInputData inputData) {
        String cityName = inputData.getCityName();

        //Basic validation: null or empty
        String trimmed = cityName == null ? null : cityName.trim();
        if (trimmed == null || trimmed.isEmpty()) {
            presenter.prepareFailView("Please check your city name and try again.");
            return;
        }
        cityName = trimmed;

        //Alternative - duplicate
        if (gateway.existsByName(cityName)) {
            boolean alreadyFavorite = true;
            SaveFavoriteLocationOutputData outputData = new SaveFavoriteLocationOutputData(
                    cityName,
                    alreadyFavorite,
                    gateway.getFavorites()
            );
            presenter.prepareSuccessView(outputData);
            return;
        }

        boolean success = gateway.saveFavorites(cityName);

        //Storage error
        if (!success) {
            presenter.prepareFailView("Unable to save location, please try again.");
            return;
        }

        //Successfully saved
        boolean isDuplicate = false;
        SaveFavoriteLocationOutputData outputData = new SaveFavoriteLocationOutputData(
                cityName,
                isDuplicate,
                gateway.getFavorites()
        );
        presenter.prepareSuccessView(outputData);

    }
}