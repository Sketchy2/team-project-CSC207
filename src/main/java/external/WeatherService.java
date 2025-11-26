package external;

import entities.Location;
import entities.WeatherData;
import entities.WeeklyData;

/**
 * High-level service for working with weather data from an external provider.
 */
public interface WeatherService {

    /**
     * Use Open-Meteo's geocoding API to find a Location from a city name.
     */
    Location searchLocation(String cityName);

    /**
     * Use Open-Meteo's weather API to fetch current weather for a Location.
     */
    WeatherData getWeather(Location location);

    WeeklyData getWeeklyData(Location location);
}

