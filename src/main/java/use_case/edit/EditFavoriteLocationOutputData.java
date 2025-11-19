package use_case.edit;

import java.util.List;

/**
 * Output data after renaming a favourite location.
 */
public class EditFavoriteLocationOutputData {

    private final List<String> favorites;
    private final String message;

    public EditFavoriteLocationOutputData(List<String> favorites, String message) {
        this.favorites = favorites;
        this.message = message;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public String getMessage() {
        return message;
    }
}
