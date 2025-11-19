package use_case.view;

public interface ViewSavedItemsOutputBoundary {
    void prepareSuccessView(ViewSavedItemsOutputData data);
    void prepareFailView(String errorMessage);
}
