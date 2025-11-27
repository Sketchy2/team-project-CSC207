package use_case;

/**
 * Input boundary for the outfit recommendation use case.
 */
public interface RecommendOutfitInputBoundary {

    /**
     * Executes the outfit recommendation request.
     *
     * @param inputData the input data containing weather information
     */
    void execute(RecommendOutfitInputData inputData);
}
