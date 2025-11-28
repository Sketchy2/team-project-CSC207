package use_case;

/**
 * Input data for the outfit recommendation use case.
 */
public class RecommendOutfitInputData {

    private final double temperature;
    private final boolean isRaining;
    private final double windSpeed;

    /**
     * Creates input data containing the weather information needed
     * for generating an outfit recommendation.
     *
     * @param temperature the temperature in Celsius
     * @param isRaining   whether it is currently raining
     * @param windSpeed   the wind speed in km/h
     */
    public RecommendOutfitInputData(double temperature, boolean isRaining, double windSpeed) {
        this.temperature = temperature;
        this.isRaining = isRaining;
        this.windSpeed = windSpeed;
    }

    /**
     * @return the temperature in Celsius
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * @return true if it is raining
     */
    public boolean isRaining() {
        return isRaining;
    }

    /**
     * @return the wind speed in km/h
     */
    public double getWindSpeed() {
        return windSpeed;
    }
}
