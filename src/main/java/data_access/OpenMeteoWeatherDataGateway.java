package data_access;

import entities.Location;
import entities.WeatherData;
import entities.WeeklyData;
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
    public Location searchLocation(String cityName) {
        return weatherService.searchLocation(cityName);
    }

    @Override
    public WeatherData fetch(Location location) {
        // We resolve the location again here or trust the caller.
        // Existing logic resolved it. We keep it safe.
        Location resolved = weatherService.searchLocation(location.getName());
        return weatherService.getWeather(resolved);
    }

    @Override
    public WeeklyData fetchWeekly(Location location) {
        Location resolved = weatherService.searchLocation(location.getName());
        return weatherService.getWeeklyData(resolved);
    }
}
