package use_case;


import entities.Location;
import entities.WeatherData;

public interface WeatherDataGateway {
    WeatherData fetch(Location location);
}



