package interface_adapters;

import use_case.RecommendOutfitOutputBoundary;
import use_case.RecommendOutfitOutputData;

/**
 * Presenter for the outfit recommendation use case.
 * Updates the RecommendationViewModel based on the output data.
 */
public class RecommendationPresenter implements RecommendOutfitOutputBoundary {

    private final RecommendationViewModel vm;

    /**
     * Creates a presenter for updating the recommendation view model.
     *
     * @param vm the view model to update
     */
    public RecommendationPresenter(RecommendationViewModel vm) {
        this.vm = vm;
    }

    /**
     * Presents a successful outfit recommendation to the view model.
     *
     * @param outputData the data containing title, items, and rationale
     */
    @Override
    public void present(RecommendOutfitOutputData outputData) {
        vm.setError(null);
        vm.setTitle(outputData.getTitle());
        vm.setItems(outputData.getItems());
        vm.setRationale(outputData.getRationale());
        vm.setChanged(true);
    }

    /**
     * Presents an error when weather data is unavailable.
     *
     * @param message the error message to display
     */
    @Override
    public void presentWeatherUnavailable(String message) {
        vm.clear();
        vm.setError(message);
        vm.setChanged(true);
    }
}
