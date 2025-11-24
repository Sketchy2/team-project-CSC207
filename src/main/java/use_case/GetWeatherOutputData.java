package use_case;

public class GetWeatherOutputData {
    private final String cityName;
    private final String countryCode;
    private final double temperature;
    private final double feelsLike;
    private final double humidityPercentage;
    private final double windSpeed;
    private final String description;
    private final boolean isRaining;
    private final String errorMessage;  // "" if success, message if error

    public GetWeatherOutputData(String cityName,
                                String countryCode,
                                double temperature,
                                double feelsLike,
                                double humidityPercentage,
                                double windSpeed,
                                String description,
                                boolean isRaining,
                                String errorMessage) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidityPercentage = humidityPercentage;
        this.windSpeed = windSpeed;
        this.description = description;
        this.isRaining = isRaining;
        this.errorMessage = errorMessage;
    }

    public String getCityName() { return cityName; }
    public String getCountryCode() { return countryCode; }
    public double getTemperature() { return temperature; }
    public double getFeelsLike() { return feelsLike; }
    public double getHumidityPercentage() { return humidityPercentage; }
    public double getWindSpeed() { return windSpeed; }
    public String getDescription() { return description; }
    public boolean isRaining() { return isRaining; }
    public String getErrorMessage() { return errorMessage; }
}


