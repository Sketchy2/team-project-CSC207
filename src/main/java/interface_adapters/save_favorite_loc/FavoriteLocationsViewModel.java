package interface_adapters.save_favorite_loc;

import java.util.List;

public class FavoriteLocationsViewModel {
    private String message;
    private List<String> favorites;

    public String getMessage() {return message;}
    public List<String> getFavorites() {return favorites;}

    public void setMessage(String message) {this.message = message;}
    public void setFavorites(List<String> favorites) {this.favorites = favorites;}

}