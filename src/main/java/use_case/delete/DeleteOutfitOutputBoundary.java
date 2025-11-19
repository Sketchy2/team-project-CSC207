package use_case.delete;

public interface DeleteOutfitOutputBoundary {
    void prepareSuccessView(DeleteOutfitOutputData data);
    void prepareFailView(String errorMessage);
}

