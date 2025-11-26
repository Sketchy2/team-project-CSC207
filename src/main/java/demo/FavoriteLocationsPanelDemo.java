package demo;

import data_access.FileFavoriteLocationsGateway;
import data_access.OpenMeteoWeatherDataGateway;
import external.OpenMeteoAPI;
import external.WeatherService;

import interface_adapters.save_favorite_loc.*;
import interface_adapters.weather.GetWeatherController;
import interface_adapters.weather.WeatherPresenter;
import interface_adapters.weather.WeatherViewModel;

import use_case.GetWeatherInputBoundary;
import use_case.GetWeatherInteractor;
import use_case.WeatherDataGateway;

import use_case.save.SaveFavoriteLocationInteractor;
import use_case.delete.DeleteFavoriteLocationInteractor;

import javax.swing.*;

public class FavoriteLocationsPanelDemo {

    public static void main(String[] args) {
        // 1. Gateway
        FileFavoriteLocationsGateway gateway =
                new FileFavoriteLocationsGateway("favorites_demo.txt");

        // 2. ViewModel
        FavoriteLocationsViewModel favoritesViewModel = new FavoriteLocationsViewModel();

        // 3. Presenter
        SaveFavoriteLocationPresenter savePresenter =
                new SaveFavoriteLocationPresenter(favoritesViewModel);
        DeleteFavoriteLocationPresenter deletePresenter =
                new DeleteFavoriteLocationPresenter(favoritesViewModel);

        // 4. Interactors
        SaveFavoriteLocationInteractor saveInteractor =
                new SaveFavoriteLocationInteractor(gateway, savePresenter);
        DeleteFavoriteLocationInteractor deleteInteractor =
                new DeleteFavoriteLocationInteractor(gateway, deletePresenter);

        // 5. Favorites controller
        FavoriteLocationsController favoritesController =
                new FavoriteLocationsController(saveInteractor, deleteInteractor);

        // 6. Weather wiring (UC1)
        WeatherService service = new OpenMeteoAPI();
        WeatherDataGateway dataGateway = new OpenMeteoWeatherDataGateway(service);

        WeatherViewModel weatherViewModel = new WeatherViewModel();
        WeatherPresenter weatherPresenter = new WeatherPresenter(weatherViewModel);

        GetWeatherInputBoundary getWeatherInteractor =
                new GetWeatherInteractor(dataGateway, weatherPresenter);
        GetWeatherController weatherController = new GetWeatherController(getWeatherInteractor);

        //7. Initial favorites into ViewModel
        favoritesViewModel.setFavorites(gateway.getFavorites());

        // 8. Panel
        FavoriteLocationsPanel panel =
                new FavoriteLocationsPanel(
                        favoritesViewModel,
                        favoritesController,
                        weatherController,
                        weatherViewModel);


        // 8. Final frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Favorite Locations Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
