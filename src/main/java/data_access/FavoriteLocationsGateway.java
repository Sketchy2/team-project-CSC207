package data_access;

import java.util.List;

public interface FavoriteLocationsGateway {
    /**Returns true if this city is already saved as a favorite.*/
    boolean existsByName(String cityName);
    void save(String cityName);
    void delete(String cityName);

    /**
     * Tries to save a favorite.
     * @return true if saved successfully, false if storage error.
     */
    boolean saveFavorites(String cityName);
    /** Returns all saved favorite city names. */
    List<String> getFavorites();


}

