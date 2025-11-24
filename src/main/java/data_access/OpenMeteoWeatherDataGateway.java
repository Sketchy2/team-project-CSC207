package data_access;

import entities.Location;
import entities.WeatherData;
import external.WeatherService;
import use_case.WeatherDataGateway;

/**
 * Real implementation of WeatherDataGateway using OpenMeteo via WeatherService.
 */
public class OpenMeteoWeatherDataGateway implements WeatherDataGateway {

    private final WeatherService weatherService;

    public OpenMeteoWeatherDataGateway(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public WeatherData fetch(Location location) {

        Location resolved = weatherService.searchLocation(location.getName());
        return weatherService.getWeather(resolved);
    }
}
