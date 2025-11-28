package use_case;

import entities.Outfit;
import java.util.List;

/**
 * Data access interface for storing and retrieving saved outfits.
 *
 * This interface belongs to the Use Case layer. Use cases depend on this
 * abstraction instead of a concrete implementation. This allows the
 * Interactor to remain independent from the database / storage mechanism.
 *
 * The Data Access implementation (e.g., in-memory, file-based)
 * will implement this interface inside the Data Access layer.
 */
public interface OutfitDataAccessInterface {

    /**
     * Save an outfit to permanent storage.
     * If an outfit with same (name, profile, location) exists,
     * it should be overwritten.
     */
    void saveOutfit(Outfit outfit);

    /**
     * Check whether an outfit with the same identifying fields already exists.
     *
     * @param name outfit name
     * @param weatherProfile profile (e.g., "rainy")
     * @param location location (e.g., "Toronto")
     * @return true if an identical identifier exists
     */
    boolean exists(String name, String weatherProfile, String location);

    /**
     * @return the full list of saved outfits.
     */
    List<Outfit> getAllOutfits();
}
