package use_case;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetWeatherInputDataTest {

    @Test
    void constructorAndGettersWork() {
        GetWeatherInputData input =
                new GetWeatherInputData("Toronto", "CA", 43.651070, -79.347015);

        assertEquals("Toronto", input.getName());
        assertEquals("CA", input.getCountryCode());
        assertEquals(43.651070, input.getLatitude(), 1e-6);
        assertEquals(-79.347015, input.getLongitude(), 1e-6);
    }
}

