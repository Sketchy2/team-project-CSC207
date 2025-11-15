package interface_adapters;

import use_case.RecommendOutfitOutputBoundary;
import use_case.RecommendOutfitOutputData;

public class RecommendationPresenter implements RecommendOutfitOutputBoundary {

    private final RecommendationViewModel vm;

    public RecommendationPresenter(RecommendationViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void present(RecommendOutfitOutputData outputData) {
        vm.setError(null);
        vm.setTitle(outputData.getTitle());
        vm.setItems(outputData.getItems());
        vm.setRationale(outputData.getRationale());
        vm.setChanged(true);
    }
    @Override
    public void presentWeatherUnavailable(String message) {
        vm.clear();
        vm.setError(message);
        vm.setChanged(true);
    }
}
