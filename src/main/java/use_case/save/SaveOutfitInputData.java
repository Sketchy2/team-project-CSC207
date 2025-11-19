package use_case.save;

import java.util.List;

/**
 * DTO sent from Controller → Interactor.
 */
public class SaveOutfitInputData {

    private final String name;
    private final List<String> items;
    private final String weatherProfile;
    private final String location;
    private final boolean overwrite;

    public SaveOutfitInputData(String name, List<String> items,
                               String weatherProfile, String location,
                               boolean overwrite) {
        this.name = name;
        this.items = items;
        this.weatherProfile = weatherProfile;
        this.location = location;
        this.overwrite = overwrite;
    }

    public String getName() { return name; }
    public List<String> getItems() { return items; }
    public String getWeatherProfile() { return weatherProfile; }
    public String getLocation() { return location; }
    public boolean isOverwrite() { return overwrite; }
}
