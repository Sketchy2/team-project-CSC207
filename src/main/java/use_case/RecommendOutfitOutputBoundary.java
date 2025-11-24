package use_case;

/**
 * Output boundary for the outfit recommendation use case.
 */
public interface RecommendOutfitOutputBoundary {

    /**
     * Presents a successful outfit recommendation.
     *
     * @param outputData the recommended items, title, and rationale
     */
    void present(RecommendOutfitOutputData outputData);

    /**
     * Presents an error when weather data is unavailable.
     *
     * @param message the error message to display
     */
    void presentWeatherUnavailable(String message);
}
