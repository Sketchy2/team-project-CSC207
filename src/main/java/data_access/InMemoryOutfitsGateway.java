package data_access;

import entities.Outfit;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of OutfitsGateway.
 *
 * Stores outfits inside an ArrayList.
 *
 * For assignment purposes, this is sufficient. In a final project,
 * this could be replaced with JSON or database storage.
 */
public class InMemoryOutfitsGateway implements OutfitsGateway {

    private final List<Outfit> outfits = new ArrayList<>();

    @Override
    public void save(Outfit outfit) {
        // Remove identical entry (same name, profile, location)
        outfits.removeIf(o ->
                o.getName().equals(outfit.getName()) &&
                        o.getWeatherProfile().equals(outfit.getWeatherProfile()) &&
                        o.getLocation().equals(outfit.getLocation())
        );
        outfits.add(outfit);
    }

    @Override
    public boolean exists(String name, String profile, String location) {
        return outfits.stream().anyMatch(o ->
                o.getName().equals(name) &&
                        o.getWeatherProfile().equals(profile) &&
                        o.getLocation().equals(location)
        );
    }

    @Override
    public List<Outfit> getAll() {
        return new ArrayList<>(outfits);
    }

    @Override
    public void delete(String name, String weatherProfile, String location) {
        outfits.removeIf(o ->
                o.getName().equals(name) &&
                        o.getWeatherProfile().equals(weatherProfile) &&
                        o.getLocation().equals(location)
        );
    }

}

