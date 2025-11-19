package use_case.view;

import entities.Outfit;
import java.util.List;

/**
 * DTO sent from Interactor → Presenter for UC6.
 */
public class ViewSavedItemsOutputData {

    private final List<Outfit> outfits;
    private final List<String> favoriteLocations;
    private final String message;

    public ViewSavedItemsOutputData(List<Outfit> outfits,
                                    List<String> favoriteLocations,
                                    String message) {
        this.outfits = outfits;
        this.favoriteLocations = favoriteLocations;
        this.message = message;
    }

    public List<Outfit> getOutfits() {
        return outfits;
    }

    public List<String> getFavoriteLocations() {
        return favoriteLocations;
    }

    public String getMessage() {
        return message;
    }
}
