package data_access;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import entities.Location;

public class FileFavoriteLocationsGateway implements FavoriteLocationsGateway {

    private final File storageFile;
    private final List<String> favorites = new ArrayList<>();

    public FileFavoriteLocationsGateway(String filename) {
        this.storageFile = new File(filename);
        loadFromFile();
    }

    /**
     * Load favorites from text file into a list.
     */
    private void loadFromFile() {
        favorites.clear();

        if (!storageFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(storageFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String city = line.trim();
                if (!city.isEmpty() && !favorites.contains(city)) {
                    favorites.add(city);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading favorites: " + e.getMessage());
        }
    }

    /**
     * Write the current favorites list back to the file.
     */
    private boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile))) {
            for (String city : favorites) {
                writer.write(city);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving favorites: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existsByName(String cityName) {
        if (cityName == null) return false;
        String trimmed = cityName.trim();
        return favorites.contains(trimmed);
    }

    @Override
    public void save(String cityName) {
        saveFavorites(cityName);
    }

    @Override
    public boolean saveFavorites(String cityName) {
        if (cityName == null) return false;
        String trimmed = cityName.trim();
        if (trimmed.isEmpty()) return false;

        if (!favorites.contains(trimmed)) {
            favorites.add(trimmed);
            boolean success = saveToFile();
            if (!success) {
                // roll back if file save failed
                favorites.remove(trimmed);
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(String cityName) {
        if (cityName == null) {
            return;
        }
        String trimmed = cityName.trim();
        if (trimmed.isEmpty()) {
            return;
        }

        if (favorites.remove(trimmed)) {
            saveToFile();
        }
    }

    @Override
    public List<String> getFavorites() {
        return new ArrayList<>(favorites);
    }
}