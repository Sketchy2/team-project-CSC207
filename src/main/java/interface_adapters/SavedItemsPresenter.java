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

    /**
     * Creates a presenter for updating the saved items view model.
     *
     * @param viewModel the view model to update
     */
    public SavedItemsPresenter(SavedItemsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Presents the saved items retrieved successfully.
     *
     * @param data output data containing saved outfits and locations
     */
    @Override
    public void prepareSuccessView(ViewSavedItemsOutputData data) {
        viewModel.setOutfits(data.getOutfits());
        viewModel.setFavoriteLocations(data.getFavoriteLocations());
        viewModel.setMessage(data.getMessage());
        viewModel.setError(null);
    }

    /**
     * Presents an error when saved items cannot be retrieved.
     *
     * @param errorMessage the error message
     */
    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setOutfits(List.of());
        viewModel.setFavoriteLocations(List.of());
        viewModel.setError(errorMessage);
    }

    /**
     * Presents the updated list of outfits after a successful delete operation.
     *
     * @param data output data containing updated outfits and success message
     */
    @Override
    public void prepareSuccessView(DeleteOutfitOutputData data) {
        viewModel.setOutfits(data.getOutfits());
        viewModel.setMessage(data.getMessage());
        viewModel.setError(null);
    }
}
