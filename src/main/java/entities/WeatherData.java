package entities;

public class WeatherData {
    private final double temperature;
    private final double feelsLike;
    private final double humidityPercentage;
    private final double windSpeed;
    private final String condition;


    public WeatherData(double temperature, double feelsLike, double humidityPercentage, double windSpeed, String condition) {
        if (humidityPercentage < 0 || humidityPercentage > 100) {
            throw new IllegalArgumentException("Humidity must be between 0 and 100");
        }
        if (windSpeed < 0) {
            throw new IllegalArgumentException("Wind must be greater than or equal to 0");
        }
        if (condition == null || condition.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition cannot be null or empty");
        }

        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidityPercentage = humidityPercentage;
        this.windSpeed = windSpeed;
        this.condition = condition.trim();
    }

    public double getTemperature() {
        return temperature;
    }
    public double getFeelsLike() {
        return feelsLike;
    }
    public double getHumidityPercentage() {
        return humidityPercentage;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public String getCondition() {
        return condition;
    }
    @Override
    public String toString() {
        return "WeatherData{" +
                ", temperature=" + temperature +
                ", feelsliketemp=" + feelsLike +
                ", humidityPercentage=" + humidityPercentage +
                ", windSpeed=" + windSpeed + '\'' +
                '}';


    }
}
