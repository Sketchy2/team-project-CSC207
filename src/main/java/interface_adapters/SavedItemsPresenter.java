package interface_adapters;

import use_case.view.ViewSavedItemsOutputBoundary;
import use_case.view.ViewSavedItemsOutputData;

import java.util.List;

/**
 * Presenter for UC6 ViewSavedItems.
 */
public class SavedItemsPresenter implements ViewSavedItemsOutputBoundary {

    private final SavedItemsViewModel viewModel;

    public SavedItemsPresenter(SavedItemsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewSavedItemsOutputData data) {
        viewModel.setOutfits(data.getOutfits());
        viewModel.setFavoriteLocations(data.getFavoriteLocations());
        viewModel.setMessage(data.getMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setOutfits(List.of());
        viewModel.setFavoriteLocations(List.of());
        viewModel.setError(errorMessage);
    }
}
