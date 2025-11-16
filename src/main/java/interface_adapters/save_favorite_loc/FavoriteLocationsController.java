package interface_adapters.save_favorite_loc;

import use_case.save_favorite.SaveFavoriteLocationInputBoundary;
import use_case.save_favorite.SaveFavoriteLocationInputData;
import use_case.delete_favorite.DeleteFavoriteLocationInputBoundary;
import use_case.delete_favorite.DeleteFavoriteLocationInputData;

public class FavoriteLocationsController {
    private final SaveFavoriteLocationInputBoundary saveInteractor;
    private final DeleteFavoriteLocationInputBoundary deleteInteractor;

    public FavoriteLocationsController(SaveFavoriteLocationInputBoundary saveInteractor,
                                       DeleteFavoriteLocationInputBoundary deleteInteractor) {
        this.saveInteractor = saveInteractor;
        this.deleteInteractor = deleteInteractor;
    }

    public void save(String cityName){
        SaveFavoriteLocationInputData inputData = new SaveFavoriteLocationInputData(cityName);
        saveInteractor.execute(inputData);
    }

    public void delete(String cityName){
        DeleteFavoriteLocationInputData inputData = new DeleteFavoriteLocationInputData(cityName);
        deleteInteractor.execute(inputData);
    }
}