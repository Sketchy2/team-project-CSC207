package test;

import data_access.FileFavoriteLocationsGateway;
import interface_adapters.save_favorite_loc.FavoriteLocationsViewModel;
import interface_adapters.save_favorite_loc.FavoriteLocationsController;
import interface_adapters.save_favorite_loc.SaveFavoriteLocationPresenter;
import use_case.delete.DeleteFavoriteLocationInputBoundary;
import use_case.delete.DeleteFavoriteLocationInteractor;
import use_case.delete.DeleteFavoriteLocationOutputBoundary;
import use_case.delete.DeleteFavoriteLocationOutputData;
import use_case.save.SaveFavoriteLocationInputBoundary;
import use_case.save.SaveFavoriteLocationInteractor;

public class FavoriteLocationsDemo {

    public static void main(String[] args) {

        // 1. Gateway (file-based persistence)
        FileFavoriteLocationsGateway gateway =
                new FileFavoriteLocationsGateway("favorites_test.txt");

        // 2. Shared ViewModel
        FavoriteLocationsViewModel viewModel = new FavoriteLocationsViewModel();

        // 3. Save Presenter + Interactor
        SaveFavoriteLocationPresenter savePresenter =
                new SaveFavoriteLocationPresenter(viewModel);
        SaveFavoriteLocationInputBoundary saveInteractor =
                new SaveFavoriteLocationInteractor(gateway, savePresenter);

        // 4. Delete Presenter (simple inline) + Interactor
        DeleteFavoriteLocationOutputBoundary deletePresenter =
                new DeleteFavoriteLocationOutputBoundary() {
                    @Override
                    public void prepareSuccessView(DeleteFavoriteLocationOutputData outputData) {
                        // For this demo, just print; we won't touch the ViewModel
                        System.out.println("[Delete Success] Deleted: "
                                + outputData.getDeletedCityName());
                        System.out.println("[Delete Success] Updated favorites: "
                                + outputData.getUpdatedFavorites());
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        System.out.println("[Delete Fail] " + errorMessage);
                    }
                };

        DeleteFavoriteLocationInputBoundary deleteInteractor =
                new DeleteFavoriteLocationInteractor(gateway, deletePresenter);

        // 5. Controller that knows both save + delete
        FavoriteLocationsController controller =
                new FavoriteLocationsController(saveInteractor, deleteInteractor);



        System.out.println("Initial favorites (file): " + gateway.getFavorites());

        System.out.println("\nSaving Toronto...");
        controller.save("Toronto");
        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());

        System.out.println("\nSaving Toronto again (duplicate)...");
        controller.save("Toronto");
        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());

        System.out.println("\nSaving Vancouver...");
        controller.save("Vancouver");
        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());

        System.out.println("\nDeleting Toronto...");
        controller.delete("Toronto");
        System.out.println("After delete, Gateway Favorites: " + gateway.getFavorites());

        System.out.println("\nDeleting city that does not exist (New York)...");
        controller.delete("New York");
        System.out.println("After failed delete, Gateway Favorites: " + gateway.getFavorites());

        System.out.println("\nDeleting empty input...");
        controller.delete("   ");
        System.out.println("After empty delete, Gateway Favorites: " + gateway.getFavorites());

        // 1. Saving an empty string
        System.out.println("\n[Extra] Saving empty string \"\"...");
        controller.save("");
        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());

        // 2. Saving only whitespace
        System.out.println("\n[Extra] Saving only spaces \"   \"...");
        controller.save("   ");
        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());

        // 3. Delete an existing city (Vancouver) to test another successful delete
        System.out.println("\n[Extra] Deleting existing city Vancouver...");
        controller.delete("Vancouver");
        System.out.println("After delete, Gateway Favorites: " + gateway.getFavorites());


        // 4. Re-add a city after deletion (Toronto)
        System.out.println("\n[Extra] Re-saving Toronto after it was deleted...");
        controller.save("Toronto");
        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());

        // 5. Try deleting the same city twice
        System.out.println("\n[Extra] Deleting Toronto the first time...");
        controller.delete("Toronto");
        System.out.println("After first delete, Gateway Favorites: " + gateway.getFavorites());

        System.out.println("\n[Extra] Deleting Toronto again (should fail)...");
        controller.delete("Toronto");
        System.out.println("After second delete attempt, Gateway Favorites: " + gateway.getFavorites());
    }
}