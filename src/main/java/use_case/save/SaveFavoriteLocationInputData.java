package use_case.save;

public class SaveFavoriteLocationInputData {
    private final String cityName;

    public SaveFavoriteLocationInputData(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }
}