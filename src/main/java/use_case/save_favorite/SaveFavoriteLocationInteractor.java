package use_case.save_favorite;

import data_access.FavoriteLocationsGateway;

public class SaveFavoriteLocationInteractor implements SaveFavoriteLocationInputBoundary {
    private final FavoriteLocationsGateway gateway;
    private final SaveFavoriteLocationOutputBoundary presenter;

    public SaveFavoriteLocationInteractor(FavoriteLocationsGateway gateway,
                                          SaveFavoriteLocationOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(SaveFavoriteLocationInputData inputData) {
        String cityName = inputData.getCityName();

        //Basic validation
        if (cityName == null || cityName.isEmpty()) {
            presenter.prepareFailView("City name cannot be empty.");
            return;
        }

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

        //Try saving
        boolean success = gateway.saveFavorites(cityName);

        //Alternative - storage error
        if (!success) {
            presenter.prepareFailView("Unable to save location, please try again.");
            return;
        }

        //Main flow - success
        boolean isDuplicate = false;
        SaveFavoriteLocationOutputData outputData = new SaveFavoriteLocationOutputData(
                cityName,
                isDuplicate,
                gateway.getFavorites()
        );
        presenter.prepareSuccessView(outputData);

    }
}