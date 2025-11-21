package use_case;

import entities.Location;
import entities.WeatherData;

public class StubWeatherDataGateway implements WeatherDataGateway {

    @Override
    public WeatherData fetch(Location location) {
        // Temporary hard-coded stub for testing UC1
        return new WeatherData(
                12.0,       // temperature
                10.0,       // feelsLike
                65.0,       // humidityPercentage
                3.2,        // windSpeed
                "Light Rain",
                true        // isRaining
        );
    }
}

