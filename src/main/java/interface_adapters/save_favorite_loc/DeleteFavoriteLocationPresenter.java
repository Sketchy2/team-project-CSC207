package interface_adapters.save_favorite_loc;

import use_case.delete.DeleteFavoriteLocationOutputBoundary;
import use_case.delete.DeleteFavoriteLocationOutputData;

public class DeleteFavoriteLocationPresenter implements DeleteFavoriteLocationOutputBoundary {

    private final FavoriteLocationsViewModel viewModel;

    public DeleteFavoriteLocationPresenter(FavoriteLocationsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(DeleteFavoriteLocationOutputData outputData) {
        viewModel.setFavorites(outputData.getUpdatedFavorites());
        viewModel.setMessage("[Delete Success] Deleted: " + outputData.getDeletedCityName());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage("[Delete Fail] " + errorMessage);
    }
}
