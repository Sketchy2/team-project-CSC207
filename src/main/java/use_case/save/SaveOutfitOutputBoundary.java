package use_case.save;

/**
 * Output Boundary (Presenter API) for Save Outfit.
 */
public interface SaveOutfitOutputBoundary {
    void prepareSuccessView(SaveOutfitOutputData data);
    void prepareFailView(String errorMessage);
}
