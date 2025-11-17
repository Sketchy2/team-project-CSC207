package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataTest {

    @Test
    void validConstructorStoresFieldsAndTrimsCondition() {
        WeatherData data = new WeatherData(
                10.0,      // temperature
                8.0,       // feelsLike
                65.0,      // humidityPercentage
                5.5,       // windSpeed
                "  Light Rain  ",
                true       // isRaining
        );

        assertEquals(10.0, data.getTemperature());
        assertEquals(8.0, data.getFeelsLike());
        assertEquals(65.0, data.getHumidityPercentage());
        assertEquals(5.5, data.getWindSpeed());
        assertEquals("Light Rain", data.getCondition());
        assertTrue(data.isRaining);
    }

    @Test
    void humidityBelowZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new WeatherData(10.0, 8.0, -1.0, 2.0, "Clear", false));
    }

    @Test
    void humidityAboveHundredThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new WeatherData(10.0, 8.0, 101.0, 2.0, "Clear", false));
    }

    @Test
    void negativeWindSpeedThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new WeatherData(10.0, 8.0, 50.0, -0.1, "Clear", false));
    }

    @Test
    void nullOrBlankConditionThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new WeatherData(10.0, 8.0, 50.0, 2.0, null, false));

        assertThrows(IllegalArgumentException.class, () ->
                new WeatherData(10.0, 8.0, 50.0, 2.0, "", false));

        assertThrows(IllegalArgumentException.class, () ->
                new WeatherData(10.0, 8.0, 50.0, 2.0, "   ", false));
    }
}
