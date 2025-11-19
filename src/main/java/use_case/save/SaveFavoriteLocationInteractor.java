package use_case.save;

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

        //Basic validation: null or empty
        if (cityName == null || cityName.isEmpty()) {
            presenter.prepareFailView("Please check your city name and try again.");
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