package use_case;

public class RecommendOutfitInputData {
    private final double temperature;
    private final boolean isRaining;
    private final double windSpeed;

    public RecommendOutfitInputData(double temperature, boolean isRaining, double windSpeed) {
    this.temperature = temperature;
    this.isRaining = isRaining;
    this.windSpeed = windSpeed;
    }
    public double getTemperature() {
        return temperature;
    }
    public boolean isRaining() {
        return isRaining;
    }
    public double getWindSpeed() {
            return windSpeed;
    }
}
