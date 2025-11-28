package use_case.save;

import java.util.List;

public class SaveFavoriteLocationOutputData {
    private final String cityName;
    private final boolean alreadyFavorite;
    private final List<String> favorites;

    public SaveFavoriteLocationOutputData(String cityName,
                                          boolean alreadyFavorite,
                                          List<String> favorites) {
        this.cityName = cityName;
        this.alreadyFavorite = alreadyFavorite;
        this.favorites = favorites;
    }
    public String getCityName() {return cityName;}

    public boolean isAlreadyFavorite() {return alreadyFavorite;}

    public List<String> getFavorites() {return favorites;}
}
