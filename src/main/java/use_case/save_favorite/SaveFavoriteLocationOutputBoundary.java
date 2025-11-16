package use_case.save_favorite;

public interface SaveFavoriteLocationOutputBoundary {

    void prepareSuccessView (SaveFavoriteLocationOutputData outputData);

    void prepareFailView (String errorMessage);
}