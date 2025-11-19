package use_case.delete;

import data_access.FavoriteLocationsGateway;
import java.util.List;

public class DeleteFavoriteLocationInteractor implements DeleteFavoriteLocationInputBoundary {
    private final FavoriteLocationsGateway gateway;
    private final DeleteFavoriteLocationOutputBoundary presenter;

    public DeleteFavoriteLocationInteractor(FavoriteLocationsGateway gateway,
                                            DeleteFavoriteLocationOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

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