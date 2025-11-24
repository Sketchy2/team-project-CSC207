package use_case.delete;

/**
 * Input data for the delete outfit use case.
 */
public class DeleteOutfitInputData {

    private final String name;
    private final String weatherProfile;
    private final String location;

    /**
     * Creates input data for deleting a saved outfit.
     *
     * @param name           the name of the outfit
     * @param weatherProfile the associated weather profile
     * @param location       the location tied to the outfit (may be null)
     */
    public DeleteOutfitInputData(String name, String weatherProfile, String location) {
        this.name = name;
        this.weatherProfile = weatherProfile;
        this.location = location;
    }

    /**
     * @return the name of the outfit to delete
     */
    public String getName() { return name; }

    /**
     * @return the weather profile of the outfit
     */
    public String getWeatherProfile() { return weatherProfile; }

    /**
     * @return the associated location, or null if none
     */
    public String getLocation() { return location; }
}
