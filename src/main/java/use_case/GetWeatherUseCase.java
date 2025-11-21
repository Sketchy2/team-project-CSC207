package use_case;

import entities.Location;
import entities.WeatherData;

public class GetWeatherUseCase {

    private final WeatherDataGateway weatherDataGateway;

    public GetWeatherUseCase(WeatherDataGateway weatherDataGateway) {
        this.weatherDataGateway = weatherDataGateway;
    }

    /**
     * For now this just prints to the console.
     * Later, this could call a presenter instead.
     */
    public void execute(Location location) {
        WeatherData data = weatherDataGateway.fetch(location);

        System.out.println("=== Weather for " + location.getName() + " ===");
        System.out.println("Temperature:      " + data.getTemperature() + " °C");
        System.out.println("Feels like:       " + data.getFeelsLike() + " °C");
        System.out.println("Humidity:         " + data.getHumidityPercentage() + " %");
        System.out.println("Wind speed:       " + data.getWindSpeed() + " m/s");
        System.out.println("Condition:      " + data.getCondition());
        System.out.println("Is it raining?:   " + (data.isRaining() ? "Yes" : "No"));
        System.out.println("==============================");
    }
}
