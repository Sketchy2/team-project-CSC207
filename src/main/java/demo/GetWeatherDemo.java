package demo;

import entities.Location;
import use_case.GetWeatherUseCase;
import use_case.StubWeatherDataGateway;
import use_case.WeatherDataGateway;

public class GetWeatherDemo {

    public static void main(String[] args) {
        // 1. Create a sample location
        Location toronto = new Location(
                "Toronto",
                "CA",// country code
                43.65107,   // latitude
                -79.347015 // longitude
        );

        // 2. Use the stub gateway for UC1
        WeatherDataGateway gateway = new StubWeatherDataGateway();

        // 3. Create the use case
        GetWeatherUseCase useCase = new GetWeatherUseCase(gateway);

        // 4. Execute UC1
        useCase.execute(toronto);
    }
}
