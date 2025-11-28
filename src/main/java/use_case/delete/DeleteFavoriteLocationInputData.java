package use_case.delete;

public class DeleteFavoriteLocationInputData {
    private final String cityName;

    public DeleteFavoriteLocationInputData(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }
}
