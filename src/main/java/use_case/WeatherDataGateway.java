package use_case;

import entities.Location;
import entities.WeatherData;
import entities.WeeklyData;

public interface WeatherDataGateway {
    // Added to allow resolving location (and country code) before fetching weather
    Location searchLocation(String cityName);
    
    WeatherData fetch(Location location);
    WeeklyData fetchWeekly(Location location);
}
