package demo;

import data_access.FileFavoriteLocationsGateway;
import interface_adapters.save_favorite_loc.*;
import use_case.save_favorite.SaveFavoriteLocationInteractor;
import use_case.delete_favorite.DeleteFavoriteLocationInteractor;

import javax.swing.*;

public class FavoriteLocationsPanelDemo {

    public static void main(String[] args) {
        // 1. Gateway
        FileFavoriteLocationsGateway gateway =
                new FileFavoriteLocationsGateway("favorites_demo.txt");

        // 2. ViewModel
        FavoriteLocationsViewModel viewModel = new FavoriteLocationsViewModel();

        // 3. Presenter
        SaveFavoriteLocationPresenter savePresenter =
                new SaveFavoriteLocationPresenter(viewModel);
        DeleteFavoriteLocationPresenter deletePresenter =
                new DeleteFavoriteLocationPresenter(viewModel);

        // 4. Interactors
        SaveFavoriteLocationInteractor saveInteractor =
                new SaveFavoriteLocationInteractor(gateway, savePresenter);
        DeleteFavoriteLocationInteractor deleteInteractor =
                new DeleteFavoriteLocationInteractor(gateway, deletePresenter);

        // 5. Combined controller
        FavoriteLocationsController controller =
                new FavoriteLocationsController(saveInteractor, deleteInteractor);

        // 6. Initial favorites into ViewModel
        viewModel.setFavorites(gateway.getFavorites());

        // 7. Panel
        FavoriteLocationsPanel panel =
                new FavoriteLocationsPanel(viewModel, controller);

        // 8. Show in a frame
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
