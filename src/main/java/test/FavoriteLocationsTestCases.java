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

/**
 * Manual test cases for the Favorite Locations save and delete use cases.
 */
public class FavoriteLocationsTestCases {

    /**
     * Entry point to run all manual test scenarios.
     *
     * @param args command-line arguments (unused)
     */
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
                    public void prepareSuccessView(
                            DeleteFavoriteLocationOutputData outputData) {
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

        // Run all scenarios
        runBasicScenario(controller, viewModel, gateway);
        runExistingExtras(controller, viewModel, gateway);
        runTrimScenario(controller, viewModel, gateway);
        runCaseSensitivityScenario(controller, viewModel, gateway);
        runInternationalScenario(controller, viewModel, gateway);
        runPersistenceScenario(controller, gateway);
    }

    /**
     * Main “happy path” tests: saving cities, avoiding duplicates,
     * and deleting existing and non-existing cities.
     */
    private static void runBasicScenario(FavoriteLocationsController controller,
                                         FavoriteLocationsViewModel viewModel,
                                         FileFavoriteLocationsGateway gateway) {

        System.out.println("Initial favorites (file): " + gateway.getFavorites());

        System.out.println("\nSaving Toronto...");
        controller.save("Toronto", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\nSaving Toronto again (duplicate)...");
        controller.save("Toronto", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\nSaving Vancouver...");
        controller.save("Vancouver", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\nDeleting Toronto...");
        controller.delete("Toronto");
        System.out.println("After delete, Gateway Favorites: " + gateway.getFavorites());

        System.out.println("\nDeleting city that does not exist (New York)...");
        controller.delete("New York");
        System.out.println("After failed delete, Gateway Favorites: "
                + gateway.getFavorites());

        System.out.println("\nDeleting empty input...");
        controller.delete("   ");
        System.out.println("After empty delete, Gateway Favorites: "
                + gateway.getFavorites());
    }

    /**
     * Your original "extra" edge cases: invalid saves, re-saving,
     * and deleting the same city twice.
     */
    private static void runExistingExtras(FavoriteLocationsController controller,
                                          FavoriteLocationsViewModel viewModel,
                                          FileFavoriteLocationsGateway gateway) {

        System.out.println("\n[Extra] Saving empty string \"\"...");
        controller.save("", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\n[Extra] Saving only spaces \"   \"...");
        controller.save("   ", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\n[Extra] Deleting existing city Vancouver...");
        controller.delete("Vancouver");
        System.out.println("After delete, Gateway Favorites: " + gateway.getFavorites());

        System.out.println("\n[Extra] Re-saving Toronto after it was deleted...");
        controller.save("Toronto", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\n[Extra] Deleting Toronto the first time...");
        controller.delete("Toronto");
        System.out.println("After first delete, Gateway Favorites: "
                + gateway.getFavorites());

        System.out.println("\n[Extra] Deleting Toronto again (should fail)...");
        controller.delete("Toronto");
        System.out.println("After second delete attempt, Gateway Favorites: "
                + gateway.getFavorites());
    }

    /**
     * Tests trimming: saving a city, then saving it again with leading/trailing
     * spaces to see if it is treated as a duplicate.
     */
    private static void runTrimScenario(FavoriteLocationsController controller,
                                        FavoriteLocationsViewModel viewModel,
                                        FileFavoriteLocationsGateway gateway) {

        System.out.println("\n[Edge] Saving Montreal without spaces...");
        controller.save("Montreal", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\n[Edge] Saving Montreal with spaces \"  Montreal  \"...");
        controller.save("  Montreal  ", "CA", 0.0, 0.0);
        printState(viewModel, gateway);
    }

    /**
     * Tests how the system behaves with different capitalization for city
     * names (e.g., "toronto" vs "Toronto").
     */
    private static void runCaseSensitivityScenario(
            FavoriteLocationsController controller,
            FavoriteLocationsViewModel viewModel,
            FileFavoriteLocationsGateway gateway) {

        System.out.println("\n[Edge] Testing case sensitivity...");

        System.out.println("\nSaving \"toronto\" (lowercase)...");
        controller.save("toronto", "CA", 0.0, 0.0);
        printState(viewModel, gateway);

        System.out.println("\nSaving \"Toronto\" (capitalized)...");
        controller.save("Toronto", "CA", 0.0, 0.0);
        printState(viewModel, gateway);
    }

    /**
     * Tests saving an international / non-ASCII city name.
     */
    private static void runInternationalScenario(
            FavoriteLocationsController controller,
            FavoriteLocationsViewModel viewModel,
            FileFavoriteLocationsGateway gateway) {

        System.out.println("\n[Edge] Saving international city \"São Paulo\"...");
        controller.save("São Paulo", "BR", 0.0, 0.0);
        printState(viewModel, gateway);
    }

    /**
     * Tests that favorites written to disk can be loaded again by a new
     * FileFavoriteLocationsGateway instance.
     */
    private static void runPersistenceScenario(FavoriteLocationsController controller,
                                               FileFavoriteLocationsGateway gateway) {

        System.out.println("\n[Edge] Testing file persistence across reload...");

        controller.save("Calgary", "CA", 0.0, 0.0);
        controller.save("Edmonton", "CA", 0.0, 0.0);
        System.out.println("Before reload, Gateway Favorites: " + gateway.getFavorites());

        FileFavoriteLocationsGateway reloadedGateway =
                new FileFavoriteLocationsGateway("favorites_test.txt");
        System.out.println("After reload, New Gateway Favorites: "
                + reloadedGateway.getFavorites());
    }

    /**
     * Helper method to print the current ViewModel message and the contents
     * of both the ViewModel and Gateway favorites.
     */
    private static void printState(FavoriteLocationsViewModel viewModel,
                                   FileFavoriteLocationsGateway gateway) {

        System.out.println("Message: " + viewModel.getMessage());
        System.out.println("ViewModel Favorites: " + viewModel.getFavorites());
        System.out.println("Gateway Favorites:   " + gateway.getFavorites());
    }
}
