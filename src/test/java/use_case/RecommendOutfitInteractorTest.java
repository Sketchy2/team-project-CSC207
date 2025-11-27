package use_case;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for {@link RecommendOutfitInteractor}.
 * These tests verify that the interactor generates correct outfit
 * recommendations based on provided weather conditions.
 */
public class RecommendOutfitInteractorTest {

    /**
     * Tests the interactor's behavior when given cold (5°C) and rainy weather.
     * Verifies that appropriate clothing items and rationale are included.
     */
    @Test
    public void testColdRainyWeatherRecommendation() {

        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {

            @Override
            public void present(RecommendOutfitOutputData outputData) {
                assertEquals("Recommended Outfit", outputData.getTitle());

                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Sweater")));
                assertTrue(items.stream().anyMatch(s -> s.contains("Puffer jacket")));
                assertTrue(items.stream().anyMatch(s -> s.contains("Boots")));

                // Rationales must mention both cold and rain
                assertTrue(outputData.getRationale().contains("Cold"));
                assertTrue(outputData.getRationale().contains("Rain"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Weather should be available for this test.");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);

        RecommendOutfitInputData input =
                new RecommendOutfitInputData(5.0, true, 10.0);

        interactor.execute(input);
    }
}
