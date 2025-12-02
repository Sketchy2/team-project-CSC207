package use_case;

import entities.WeeklyData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetWeatherOutputDataTest {

    @Test
    void constructorAndGettersWork() {
        WeeklyData weekly = null;

        GetWeatherOutputData out = new GetWeatherOutputData(
                "Paris",
                "FR",
                10.0,
                8.0,
                75.0,
                3.0,
                "Cloudy",
                false,
                weekly,
                ""
        );

        assertEquals("Paris", out.getCityName());
        assertEquals("FR", out.getCountryCode());
        assertEquals(10.0, out.getTemperature(), 1e-6);
        assertEquals(8.0, out.getFeelsLike(), 1e-6);
        assertEquals(75.0, out.getHumidityPercentage(), 1e-6);
        assertEquals(3.0, out.getWindSpeed(), 1e-6);
        assertEquals("Cloudy", out.getDescription());
        assertFalse(out.isRaining());
        assertEquals(weekly, out.getWeeklyData());
        assertEquals("", out.getErrorMessage());
    }
}

