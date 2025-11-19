package use_case.delete;

public interface DeleteFavoriteLocationOutputBoundary {
    void prepareSuccessView(DeleteFavoriteLocationOutputData outputData);
    void prepareFailView(String errorMessage);
}