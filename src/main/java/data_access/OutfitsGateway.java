package data_access;

import entities.Outfit;
import java.util.List;

/**
 * Gateway interface for saving, retrieving, and checking
 * saved outfits in the application.
 *
 * This is the Data Access Boundary for the Save Outfit use case.
 */
public interface OutfitsGateway {

    /**
     * Save an outfit to persistent storage.
     * If an identical outfit exists (same name, weatherProfile, location),
     * the old one should be overwritten.
     */
    void save(Outfit outfit);

    /**
     * Check if an outfit already exists with the same name, weatherProfile, and location.
     *
     * @return true if found
     */
    boolean exists(String name, String weatherProfile, String location);

    /**
     * @return all saved outfits
     */
    List<Outfit> getAll();

    /**
     * Delete an outfit that matches the given identifiers.
     */
    void delete(String name, String weatherProfile, String location);
}
