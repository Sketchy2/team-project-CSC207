package use_case;

import entities.Location;
import entities.WeatherData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GetWeatherUseCase.
 */
class GetWeatherUseCaseTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Fake gateway that returns non-raining weather data.
     */
    private static class FakeGatewayNotRaining implements WeatherDataGateway {

        @Override
        public Location searchLocation(String cityName) {
            return null;
        }

        @Override
        public WeatherData fetch(Location location) {
            return new WeatherData(
                    20.0,   // temperature
                    18.0,   // feelsLike
                    50.0,   // humidityPercentage
                    4.0,    // windSpeed
                    "Sunny",
                    false   // isRaining
            );
        }

        @Override
        public entities.WeeklyData fetchWeekly(Location location) {
            // Not used in GetWeatherUseCase.
            return null;
        }
    }

    /**
     * Fake gateway that returns raining weather data.
     */
    private static class FakeGatewayRaining implements WeatherDataGateway {

        @Override
        public Location searchLocation(String cityName) {
            return null;
        }

        @Override
        public WeatherData fetch(Location location) {
            return new WeatherData(
                    5.0,
                    3.0,
                    90.0,
                    7.0,
                    "Rain",
                    true    // isRaining
            );
        }

        @Override
        public entities.WeeklyData fetchWeekly(Location location) {
            return null;
        }
    }

    @Test
    void printsExpectedOutputWhenNotRaining() {
        WeatherDataGateway gateway = new FakeGatewayNotRaining();
        GetWeatherUseCase useCase = new GetWeatherUseCase(gateway);
        Location location = new Location("Toronto", "CA", 43.0, -79.0);

        useCase.execute(location);

        String output = outContent.toString();

        assertTrue(output.contains("=== Weather for Toronto ==="));
        assertTrue(output.contains("Temperature:      20.0"));
        assertTrue(output.contains("Feels like:       18.0"));
        assertTrue(output.contains("Humidity:         50.0"));
        assertTrue(output.contains("Wind speed:       4.0"));
        assertTrue(output.contains("Condition:      Sunny"));
        assertTrue(output.contains("Is it raining?:   No"));
    }

    @Test
    void printsExpectedOutputWhenRaining() {
        WeatherDataGateway gateway = new FakeGatewayRaining();
        GetWeatherUseCase useCase = new GetWeatherUseCase(gateway);
        Location location = new Location("Vancouver", "CA", 49.0, -123.0);

        useCase.execute(location);

        String output = outContent.toString();

        assertTrue(output.contains("=== Weather for Vancouver ==="));
        assertTrue(output.contains("Condition:      Rain"));
        assertTrue(output.contains("Is it raining?:   Yes"));
    }
}
