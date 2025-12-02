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

    @Test
    public void testFreezingWeather() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Thermal base layer")));
                assertTrue(items.stream().anyMatch(s -> s.contains("Heavy winter coat")));
                assertTrue(outputData.getRationale().contains("Freezing"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(-5.0, false, 10.0));
    }

    @Test
    public void testCoolWeather() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Light jacket")));
                assertTrue(outputData.getRationale().contains("Cool"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(10.0, false, 10.0));
    }

    @Test
    public void testMildWeather() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("T-shirt")));
                assertTrue(outputData.getRationale().contains("Mild"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(20.0, false, 10.0));
    }

    @Test
    public void testWarmWeather() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Breathable T-shirt")));
                assertTrue(items.stream().anyMatch(s -> s.contains("Sandals")));
                assertTrue(outputData.getRationale().contains("Warm"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(30.0, false, 10.0));
    }

    @Test
    public void testWindyCondition() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Windbreaker")));
                assertTrue(outputData.getRationale().contains("Windy"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(20.0, false, 35.0));
    }

    @Test
    public void testWeatherUnavailable() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                fail("Should have presented weather unavailable");
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                assertEquals("Weather unavailable, please refresh.", message);
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(Double.NaN, false, 0.0));
    }

    @Test
    public void testRainingAndWindy() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Waterproof jacket")));
                assertTrue(items.stream().anyMatch(s -> s.contains("Windbreaker")));

                String rationale = outputData.getRationale();
                assertTrue(rationale.contains("Rain"));
                assertTrue(rationale.contains("Windy"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(15.0, true, 40.0));
    }

    @Test
    public void testWindBoundary() {
        RecommendOutfitOutputBoundary presenter = new RecommendOutfitOutputBoundary() {
            @Override
            public void present(RecommendOutfitOutputData outputData) {
                List<String> items = outputData.getItems();
                assertTrue(items.stream().anyMatch(s -> s.contains("Windbreaker")));
                assertTrue(outputData.getRationale().contains("Windy"));
            }

            @Override
            public void presentWeatherUnavailable(String message) {
                fail("Unexpected weather unavailable");
            }
        };

        RecommendOutfitInteractor interactor = new RecommendOutfitInteractor(presenter);
        interactor.execute(new RecommendOutfitInputData(20.0, false, 30.0));
    }
}
