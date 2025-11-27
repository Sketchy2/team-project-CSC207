package use_case;

import entities.Location;
import entities.WeatherData;
import entities.WeeklyData;

public interface WeatherDataGateway {
    WeatherData fetch(Location location);
    WeeklyData fetchWeekly(Location location);
}
