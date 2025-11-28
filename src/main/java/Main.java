import data_access.*;
import external.*;
import interface_adapters.*;
import interface_adapters.weather.*;
import interface_adapters.save_favorite_loc.*;
import use_case.*;
import use_case.delete.*;
import use_case.edit.*;
import use_case.save.*;
import use_case.view.*;
import view.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Data Access / Services
            WeatherService weatherService = new OpenMeteoAPI();
            WeatherDataGateway weatherGateway =
                    new OpenMeteoWeatherDataGateway(weatherService);

            FavoriteLocationsGateway favLocGateway =
                    new FileFavoriteLocationsGateway("favorites.json");
            OutfitsGateway outfitsGateway =
                    new FileOutfitsGateway("outfits.json");

            // 2. ViewModels
            WeatherViewModel weatherViewModel = new WeatherViewModel();
            RecommendationViewModel recommendationViewModel =
                    new RecommendationViewModel();
            SavedItemsViewModel savedItemsViewModel = new SavedItemsViewModel();
            SaveOutfitViewModel saveOutfitViewModel = new SaveOutfitViewModel();
            FavoriteLocationsViewModel favLocViewModel =
                    new FavoriteLocationsViewModel();

            // 3. Presenters
            WeatherPresenter weatherPresenter =
                    new WeatherPresenter(weatherViewModel);
            RecommendationPresenter recommendationPresenter =
                    new RecommendationPresenter(recommendationViewModel);

            SavedItemsPresenter savedItemsPresenter =
                    new SavedItemsPresenter(savedItemsViewModel);
            SaveOutfitPresenter saveOutfitPresenter =
                    new SaveOutfitPresenter(saveOutfitViewModel);

            SaveFavoriteLocationPresenter saveFavPresenter =
                    new SaveFavoriteLocationPresenter(favLocViewModel);
            DeleteFavoriteLocationPresenter deleteFavPresenter =
                    new DeleteFavoriteLocationPresenter(favLocViewModel);
            EditFavoriteLocationPresenter editFavPresenter =
                    new EditFavoriteLocationPresenter(savedItemsViewModel);

            // 4. Interactors
            GetWeatherInputBoundary getWeatherInteractor =
                    new GetWeatherInteractor(weatherGateway, weatherPresenter);
            RecommendOutfitInputBoundary recommendOutfitInteractor =
                    new RecommendOutfitInteractor(recommendationPresenter);

            SaveOutfitInputBoundary saveOutfitInteractor =
                    new SaveOutfitInteractor(outfitsGateway, saveOutfitPresenter);
            DeleteOutfitInputBoundary deleteOutfitInteractor =
                    new DeleteOutfitInteractor(outfitsGateway, savedItemsPresenter);

            SaveFavoriteLocationInputBoundary saveFavInteractor =
                    new SaveFavoriteLocationInteractor(favLocGateway, saveFavPresenter);
            DeleteFavoriteLocationInputBoundary deleteFavInteractor =
                    new DeleteFavoriteLocationInteractor(favLocGateway, deleteFavPresenter);
            EditFavoriteLocationInputBoundary editFavInteractor =
                    new EditFavoriteLocationInteractor(favLocGateway, editFavPresenter);
            ViewSavedItemsInputBoundary viewSavedInteractor =
                    new ViewSavedItemsInteractor(outfitsGateway, favLocGateway, savedItemsPresenter);

            // 5. Controllers
            GetWeatherController getWeatherController = new GetWeatherController(getWeatherInteractor);
            RecommendOutfitController recommendOutfitController =
                    new RecommendOutfitController(recommendOutfitInteractor);
            SaveOutfitController saveOutfitController = new SaveOutfitController(saveOutfitInteractor);
            DeleteOutfitController deleteOutfitController = new DeleteOutfitController(deleteOutfitInteractor);

            FavoriteLocationsController favLocController =
                    new FavoriteLocationsController(saveFavInteractor, deleteFavInteractor);
            EditFavoriteLocationController editFavController = new EditFavoriteLocationController(editFavInteractor);
            SavedItemsController savedItemsController = new SavedItemsController(viewSavedInteractor);

            // 6. Views

            // Weather Panel
            WeatherPanel weatherPanel = new WeatherPanel(getWeatherController, weatherViewModel);

            // Recommendation Panel
            RecommendationPanel recommendationPanel = new RecommendationPanel(
                    recommendOutfitController,
                    recommendationViewModel,
                    weatherViewModel,
                    saveOutfitController,
                    saveOutfitViewModel
            );

            // Saved Items View
            SavedItemsView savedItemsView = new SavedItemsView(
                    savedItemsController,
                    editFavController,
                    saveOutfitController,
                    deleteOutfitController,
                    savedItemsViewModel,
                    saveOutfitViewModel
            );

            // 7. Main Frame Wiring
            JFrame frame = new JFrame("Weather2Wear");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            // Card Layout for switching between Dashboard and Saved Items
            CardLayout cardLayout = new CardLayout();
            JPanel mainContainer = new JPanel(cardLayout);

            // --- Dashboard ---
            JPanel dashboardPanel = new JPanel(new BorderLayout());

            // Top Controls (Buttons)
            JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton viewSavedButton = new JButton("View Saved Items");
            JButton saveLocationButton = new JButton("Save Location");

            topBar.add(viewSavedButton);
            topBar.add(saveLocationButton);
            dashboardPanel.add(topBar, BorderLayout.NORTH);

            // Split Pane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, weatherPanel, recommendationPanel);
            splitPane.setDividerLocation(500);
            splitPane.setResizeWeight(0.5);
            dashboardPanel.add(splitPane, BorderLayout.CENTER);

            // --- Saved Items Screen ---
            JPanel savedItemsWrapper = new JPanel(new BorderLayout());
            JButton backButton = new JButton("← Back to Dashboard");
            savedItemsWrapper.add(backButton, BorderLayout.NORTH);
            savedItemsWrapper.add(savedItemsView, BorderLayout.CENTER);

            mainContainer.add(dashboardPanel, "DASHBOARD");
            mainContainer.add(savedItemsWrapper, "SAVED");

            frame.add(mainContainer);

            // Navigation Listeners
            viewSavedButton.addActionListener(e -> {
                cardLayout.show(mainContainer, "SAVED");
                savedItemsController.loadSavedItems();
            });

            backButton.addActionListener(e -> {
                cardLayout.show(mainContainer, "DASHBOARD");
            });

            saveLocationButton.addActionListener(e -> {
                WeatherViewModel.State state = weatherViewModel.getState();
                if (state.cityName == null || state.cityName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No city to save. Get weather first.");
                    return;
                }
                String full = state.cityName;
                String name = full;
                // Parsing "City (COUNTRY)" format if present
                if (full.contains("(") && full.endsWith(")")) {
                    int idx = full.lastIndexOf("(");
                    if (idx > 0) {
                        name = full.substring(0, idx).trim();
                    }
                }

                favLocController.save(name, "CA", 0.0, 0.0);
                JOptionPane.showMessageDialog(frame, "Location saved: " + name);
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

