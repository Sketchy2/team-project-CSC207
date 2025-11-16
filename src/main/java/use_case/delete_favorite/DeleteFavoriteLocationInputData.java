package use_case.delete_favorite;

public class DeleteFavoriteLocationInputData {
    private final String cityName;

    public DeleteFavoriteLocationInputData(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }
}