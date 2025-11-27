package interface_adapters;

import use_case.RecommendOutfitInputBoundary;
import use_case.RecommendOutfitInputData;

/**
 * Controller for the outfit recommendation use case.
 */
public class RecommendOutfitController {

    private final RecommendOutfitInputBoundary interactor;

    /**
     * Creates a controller for recommending outfits.
     *
     * @param interactor the input boundary handling the recommendation logic
     */
    public RecommendOutfitController(RecommendOutfitInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the outfit recommendation use case.
     *
     * @param temperatureC   the current temperature in Celsius
     * @param isRaining      whether it is raining
     * @param windSpeedKmh   wind speed in km/h
     */
    public void recommend(double temperatureC, boolean isRaining, double windSpeedKmh){
        RecommendOutfitInputData data = new RecommendOutfitInputData(temperatureC, isRaining, windSpeedKmh);
        interactor.execute(data);
    }
}
