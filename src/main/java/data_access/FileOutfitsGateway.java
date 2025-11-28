package data_access;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entities.Outfit;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileOutfitsGateway implements OutfitsGateway {

    private final File storageFile;
    private final Gson gson = new Gson();
    private final List<Outfit> outfits = new ArrayList<>();

    public FileOutfitsGateway(String filePath) {
        this.storageFile = new File(filePath);
        loadFromFile();
    }

    private void loadFromFile() {
        outfits.clear();
        if (!storageFile.exists()) {
            return;
        }

        try (Reader reader = new FileReader(storageFile)) {
            Type listType = new TypeToken<List<Outfit>>() {}.getType();
            List<Outfit> loaded = gson.fromJson(reader, listType);
            if (loaded != null) {
                outfits.addAll(loaded);
            }
        } catch (IOException e) {
            System.err.println("Error loading outfits: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(storageFile)) {
            gson.toJson(outfits, writer);
        } catch (IOException e) {
            System.err.println("Error saving outfits: " + e.getMessage());
        }
    }

    @Override
    public void save(Outfit outfit) {
        // Remove duplicate if exists
        delete(outfit.getName(), outfit.getWeatherProfile(), outfit.getLocation());
        outfits.add(outfit);
        saveToFile();
    }

    @Override
    public boolean exists(String name, String weatherProfile, String location) {
        return outfits.stream().anyMatch(o ->
                Objects.equals(o.getName(), name) &&
                        Objects.equals(o.getWeatherProfile(), weatherProfile) &&
                        Objects.equals(o.getLocation(), location)
        );
    }

    @Override
    public List<Outfit> getAll() {
        return new ArrayList<>(outfits);
    }

    @Override
    public void delete(String name, String weatherProfile, String location) {
        boolean removed = outfits.removeIf(o ->
                Objects.equals(o.getName(), name) &&
                        Objects.equals(o.getWeatherProfile(), weatherProfile) &&
                        Objects.equals(o.getLocation(), location)
        );
        if (removed) {
            saveToFile();
        }
    }
}
