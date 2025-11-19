package interface_adapters;

import use_case.view.ViewSavedItemsOutputBoundary;
import use_case.view.ViewSavedItemsOutputData;
import use_case.delete.DeleteOutfitOutputBoundary;
import use_case.delete.DeleteOutfitOutputData;
import java.util.List;

/**
 * Presenter for UC6 ViewSavedItems.
 */
public class SavedItemsPresenter implements ViewSavedItemsOutputBoundary, DeleteOutfitOutputBoundary{

    private final SavedItemsViewModel viewModel;

    public SavedItemsPresenter(SavedItemsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewSavedItemsOutputData data) {
        viewModel.setOutfits(data.getOutfits());
        viewModel.setFavoriteLocations(data.getFavoriteLocations());
        viewModel.setMessage(data.getMessage());
        viewModel.setError(null);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setOutfits(List.of());
        viewModel.setFavoriteLocations(List.of());
        viewModel.setError(errorMessage);
    }

    // For Delete Outfit feauture
    @Override
    public void prepareSuccessView(DeleteOutfitOutputData data) {
        viewModel.setOutfits(data.getOutfits());
        viewModel.setMessage(data.getMessage());
        viewModel.setError(null);
    }
}
