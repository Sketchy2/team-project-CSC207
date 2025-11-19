package interface_adapters;

import use_case.edit.EditFavoriteLocationOutputBoundary;
import use_case.edit.EditFavoriteLocationOutputData;

/**
 * Presenter for EditFavoriteLocation.
 * Updates the SavedItemsViewModel.
 */
public class EditFavoriteLocationPresenter implements EditFavoriteLocationOutputBoundary {

    private final SavedItemsViewModel viewModel;

    public EditFavoriteLocationPresenter(SavedItemsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(EditFavoriteLocationOutputData data) {
        viewModel.setFavoriteLocations(data.getFavorites());
        viewModel.setMessage(data.getMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Keep existing favourites; just show an error
        viewModel.setError(errorMessage);
    }
}
