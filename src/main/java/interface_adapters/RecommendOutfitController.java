package interface_adapters;

import use_case.RecommendOutfitInputBoundary;
import use_case.RecommendOutfitInputData;

public class RecommendOutfitController {
    private final RecommendOutfitInputBoundary interactor;

    public RecommendOutfitController(RecommendOutfitInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void recommend(double temperatureC, boolean isRaining, double windSpeedKmh){
        RecommendOutfitInputData data = new RecommendOutfitInputData(temperatureC, isRaining, windSpeedKmh);
        interactor.execute(data);
    }
}
