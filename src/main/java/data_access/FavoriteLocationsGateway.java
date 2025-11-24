package data_access;

import java.util.List;

public interface FavoriteLocationsGateway {
    /**Returns true if this city is already saved as a favorite.*/
    boolean existsByName(String cityName);

    void save(String cityName);

    /**
     * Tries to add a favorite.
     * @return true if saved successfully, false if storage error.
     */
    boolean saveFavorites(String cityName);

    /** Delete a favorite city by name. */
    void delete(String cityName);

    /** Returns all saved favorite city names. */
    List<String> getFavorites();

}

