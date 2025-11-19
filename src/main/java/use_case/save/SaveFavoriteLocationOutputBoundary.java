package use_case.save;

public interface SaveFavoriteLocationOutputBoundary {

    void prepareSuccessView (SaveFavoriteLocationOutputData outputData);

    void prepareFailView (String errorMessage);
}