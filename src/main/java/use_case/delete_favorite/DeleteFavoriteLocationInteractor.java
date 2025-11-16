package use_case.delete_favorite;

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

        if (rawCityName == null) {
            presenter.prepareFailView("City name cannot be null.");
            return;
        }

        String cityName = rawCityName.trim();
        if (cityName.isEmpty()) {
            presenter.prepareFailView("City name cannot be empty.");
            return;
        }

        if (!gateway.existsByName(cityName)) {
            presenter.prepareFailView("Location not found in favorites: " + cityName);
            return;
        }

        gateway.delete(cityName);

        List<String> updatedFavorites = gateway.getFavorites();

        DeleteFavoriteLocationOutputData output = new DeleteFavoriteLocationOutputData(cityName,
                updatedFavorites);

        presenter.prepareSuccessView(output);
    }
}