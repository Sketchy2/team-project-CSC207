package use_case.delete_favorite;

public interface DeleteFavoriteLocationOutputBoundary {
    void prepareSuccessView(DeleteFavoriteLocationOutputData outputData);
    void prepareFailView(String errorMessage);
}