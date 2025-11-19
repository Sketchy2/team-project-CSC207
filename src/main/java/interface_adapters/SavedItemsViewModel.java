package interface_adapters;

import entities.Outfit;
import java.util.List;

/**
 * UI-facing state for the Saved Items screen.
 */
public class SavedItemsViewModel {

    private List<Outfit> outfits;
    private List<String> favoriteLocations;
    private String message = "";
    private String error = "";

    public List<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(List<Outfit> outfits) {
        this.outfits = outfits;
    }

    public List<String> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(List<String> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.error = "";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        this.message = "";
    }
}
