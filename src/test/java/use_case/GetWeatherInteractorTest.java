package use_case;

import entities.Location;
import entities.WeatherData;
import entities.WeeklyData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GetWeatherInteractor.
 */
class GetWeatherInteractorTest {

    /**
     * Fake gateway that simulates a successful API response.
     */
    private static class FakeSuccessGateway implements WeatherDataGateway {

        @Override
        public Location searchLocation(String cityName) {
            return new Location(cityName, "FR", 0.0, 0.0);
        }

        @Override
        public WeatherData fetch(Location location) {
            return new WeatherData(
                    10.0,      // temperature
                    8.0,       // feelsLike
                    75.0,      // humidityPercentage
                    3.0,       // windSpeed
                    "Cloudy",  // description/condition
                    false      // isRaining
            );
        }

        @Override
        public WeeklyData fetchWeekly(Location location) {
            return null;
        }
    }

    /**
     * Fake gateway that simulates a failure when fetching weather.
     */
    private static class FakeFailGateway implements WeatherDataGateway {

        @Override
        public Location searchLocation(String cityName) {
            return new Location(cityName, "XX", 0.0, 0.0);
        }

        @Override
        public WeatherData fetch(Location location) {
            throw new RuntimeException("City not found");
        }

        @Override
        public WeeklyData fetchWeekly(Location location) {
            return null;
        }
    }

    /**
     * Fake presenter that just captures the last output data passed to it.
     */
    private static class FakePresenter implements GetWeatherOutputBoundary {

        private GetWeatherOutputData lastOutput;

        @Override
        public void present(GetWeatherOutputData outputData) {
            this.lastOutput = outputData;
        }

        public GetWeatherOutputData getLastOutput() {
            return lastOutput;
        }
    }

    @Test
    void usesResolvedLocationAndPopulatesWeather() {
        // Arrange
        WeatherDataGateway gateway = new FakeSuccessGateway();
        FakePresenter presenter = new FakePresenter();
        GetWeatherInteractor interactor = new GetWeatherInteractor(gateway, presenter);

        GetWeatherInputData input =
                new GetWeatherInputData("Paris", "CA", 0.0, 0.0);

        interactor.execute(input);

        GetWeatherOutputData out = presenter.getLastOutput();
        assertNotNull(out, "Presenter should receive output data");

        assertEquals("Paris", out.getCityName());
        assertEquals("FR", out.getCountryCode());

        assertEquals(10.0, out.getTemperature(), 1e-6);
        assertEquals(8.0, out.getFeelsLike(), 1e-6);
        assertEquals(75.0, out.getHumidityPercentage(), 1e-6);
        assertEquals(3.0, out.getWindSpeed(), 1e-6);
        assertEquals("Cloudy", out.getDescription());
        assertFalse(out.isRaining());

        assertEquals("", out.getErrorMessage());
    }

    @Test
    void populatesErrorMessageAndKeepsInputLocation() {
        WeatherDataGateway gateway = new FakeFailGateway();
        FakePresenter presenter = new FakePresenter();
        GetWeatherInteractor interactor = new GetWeatherInteractor(gateway, presenter);

        GetWeatherInputData input =
                new GetWeatherInputData("NowhereCity", "CA", 0.0, 0.0);

        interactor.execute(input);

        GetWeatherOutputData out = presenter.getLastOutput();
        assertNotNull(out, "Presenter should receive output data even on failure");

        assertEquals("NowhereCity", out.getCityName());
        assertEquals("CA", out.getCountryCode());

        assertEquals(0.0, out.getTemperature(), 1e-6);
        assertEquals(0.0, out.getFeelsLike(), 1e-6);
        assertEquals(0.0, out.getHumidityPercentage(), 1e-6);
        assertEquals(0.0, out.getWindSpeed(), 1e-6);
        assertEquals("", out.getDescription());
        assertFalse(out.isRaining());
        assertNull(out.getWeeklyData());

        assertEquals("Could not find weather for that city.", out.getErrorMessage());
    }
}
