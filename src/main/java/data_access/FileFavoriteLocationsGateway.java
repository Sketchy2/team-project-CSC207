package data_access;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    /** Write the current favorite list back to the file. */
    private boolean saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile))){
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
        return favorites.contains(cityName);
    }

    @Override
    public void save(String cityName) {
        saveFavorites(cityName);
    }

    @Override
    public boolean saveFavorites(String cityName) {
        if (!favorites.contains(cityName)) {
            favorites.add(cityName);
            boolean success = saveToFile();
            if (!success) {
                favorites.remove(cityName);
                return false;
            }
        }
        return true;
    }
    @Override
    public List<String> getFavorites() {
        return new ArrayList<>(favorites);
    }

    @Override
    public void delete(String cityName) {
        if (cityName == null) {
            return;
        }
        String trimmed =  cityName.trim();
        if (trimmed.isEmpty()) {
            return;
        }

        if (favorites.remove(trimmed)) {
            saveToFile();
        }
    }
}
