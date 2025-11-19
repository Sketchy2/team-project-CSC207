package use_case.delete;

import java.util.List;

public class DeleteFavoriteLocationOutputData {

    private final String deletedCityName;
    private final List<String> updatedFavorites;

    public DeleteFavoriteLocationOutputData(String deletedCityName, List<String> updatedFavorites) {
        this.deletedCityName = deletedCityName;
        this.updatedFavorites = updatedFavorites;
    }

    public String getDeletedCityName() {return deletedCityName;}

    public List<String> getUpdatedFavorites() {return updatedFavorites;}
}