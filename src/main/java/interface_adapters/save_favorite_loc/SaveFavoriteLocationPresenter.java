package interface_adapters.save_favorite_loc;

import use_case.save_favorite.SaveFavoriteLocationOutputBoundary;
import use_case.save_favorite.SaveFavoriteLocationOutputData;

public class SaveFavoriteLocationPresenter implements SaveFavoriteLocationOutputBoundary{

    private final FavoriteLocationsViewModel viewModel;

    public SaveFavoriteLocationPresenter(FavoriteLocationsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SaveFavoriteLocationOutputData outputData){
        if (outputData.isAlreadyFavorite()){
            viewModel.setMessage("Already Favorite");
        } else {
            viewModel.setMessage("Saved " + outputData.getCityName());
        }
        viewModel.setFavorites(outputData.getFavorites());
    }

    @Override
    public void prepareFailView(String errorMessage){
        viewModel.setMessage(errorMessage);
    }
}