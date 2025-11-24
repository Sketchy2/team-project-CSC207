package use_case.delete;

public class DeleteOutfitInputData {
    private final String name;
    private final String weatherProfile;
    private final String location;

    public DeleteOutfitInputData(String name, String weatherProfile, String location) {
        this.name = name;
        this.weatherProfile = weatherProfile;
        this.location = location;
    }

    public String getName() { return name; }
    public String getWeatherProfile() { return weatherProfile; }
    public String getLocation() { return location; }
}
