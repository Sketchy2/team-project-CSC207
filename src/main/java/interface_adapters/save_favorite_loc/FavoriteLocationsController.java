package interface_adapters.save_favorite_loc;

import use_case.delete.DeleteFavoriteLocationInputBoundary;
import use_case.delete.DeleteFavoriteLocationInputData;
import use_case.save.SaveFavoriteLocationInputBoundary;
import use_case.save.SaveFavoriteLocationInputData;

public class FavoriteLocationsController {
    private final SaveFavoriteLocationInputBoundary saveInteractor;
    private final DeleteFavoriteLocationInputBoundary deleteInteractor;

    public FavoriteLocationsController(SaveFavoriteLocationInputBoundary saveInteractor,
                                       DeleteFavoriteLocationInputBoundary deleteInteractor) {
        this.saveInteractor = saveInteractor;
        this.deleteInteractor = deleteInteractor;
    }

    public void save(String cityName,
                     String countryCode,
                     double latitude,
                     double longitude){

        SaveFavoriteLocationInputData inputData = new SaveFavoriteLocationInputData(
                cityName,
                countryCode,
                latitude,
                longitude);
        saveInteractor.execute(inputData);
    }

    public void delete(String cityName){
        DeleteFavoriteLocationInputData inputData = new DeleteFavoriteLocationInputData(cityName);
        deleteInteractor.execute(inputData);
    }
}