package use_case;

public interface RecommendOutfitOutputBoundary {
    void present(RecommendOutfitOutputData outputData);

    void presentWeatherUnavailable(String message);

}
