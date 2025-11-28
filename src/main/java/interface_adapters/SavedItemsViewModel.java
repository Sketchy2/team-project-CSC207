package interface_adapters;

import entities.Outfit;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * UI-facing state for the Saved Items screen.
 */
public class SavedItemsViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private List<Outfit> outfits;
    private List<String> favoriteLocations;
    private String message = "";
    private String error = "";

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public List<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(List<Outfit> outfits) {
        List<Outfit> old = this.outfits;
        this.outfits = outfits;
        support.firePropertyChange("outfits", old, outfits);
    }

    public List<String> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(List<String> favoriteLocations) {
        List<String> old = this.favoriteLocations;
        this.favoriteLocations = favoriteLocations;
        support.firePropertyChange("favoriteLocations", old, favoriteLocations);
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        String old = this.message;
        this.message = (message == null) ? "" : message;
        this.error = "";
        support.firePropertyChange("message", old, this.message);
    }

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        String old = this.error;
        this.error = (error == null) ? "" : error;
        this.message = "";
        support.firePropertyChange("error", old, this.error);
    }
}
