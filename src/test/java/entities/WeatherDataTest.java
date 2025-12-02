package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherDataTest {

    @Test
    void constructorAndGettersWork() {
        WeatherData data = new WeatherData(
                10.0,
                8.0,
                75.0,
                3.0,
                "Cloudy",
                false
        );

        assertEquals(10.0, data.getTemperature(), 1e-6);
        assertEquals(8.0, data.getFeelsLike(), 1e-6);
        assertEquals(75.0, data.getHumidityPercentage(), 1e-6);
        assertEquals(3.0, data.getWindSpeed(), 1e-6);
        assertEquals("Cloudy", data.getCondition());
        assertFalse(data.isRaining());
    }
}
