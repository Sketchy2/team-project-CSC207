package interface_adapters.save_favorite_loc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import entities.Location;

public class FavoriteLocationsViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String message;
    private List<String> favorites;

    public String getMessage() {
        return message;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setMessage(String message) {
        String oldMessage = this.message;
        this.message = message;
        support.firePropertyChange("message", oldMessage, message);
    }

    public void setFavorites(List<String> favorites) {
        List<String> old = this.favorites;
        this.favorites = favorites;
        support.firePropertyChange("favorites", old, favorites);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}