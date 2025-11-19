package use_case.edit;

public interface EditFavoriteLocationOutputBoundary {
    void prepareSuccessView(EditFavoriteLocationOutputData data);
    void prepareFailView(String errorMessage);
}
