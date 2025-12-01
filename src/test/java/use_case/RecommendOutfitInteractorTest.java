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
    public void testFallbackEmptyItems() {
        // This test case is theoretically unreachable with current logic because every temp range adds items.
        // However, to force coverage if the logic changes or to test the safety net:
        // We can simulate a case where items list is cleared or fails to populate (e.g. if logic was different).
        // With current logic, we can't easily trigger the "if (items.isEmpty())" block because the temperature checks are exhaustive (else covers >= 26).
        // But let's double check the logic.
        // < 0, <= 5, <= 15, <= 25, else (>=26). All doubles are covered except NaN (handled at start).
        // So items will never be empty unless we manually break the logic.
        // The block lines 90-95 in RecommendOutfitInteractor are effectively dead code unless we introduce a gap in temp ranges.
        // Or if we pass a temperature that skips all blocks? No, 'else' catches everything.
        // So for 100% coverage of *reachable* lines, we are good.
        // If we want to cover line 90-95, we'd need to modify the source code to have a gap, or accept it's dead code.
        // Wait, let's check if there's any edge case.
        // What if we have a subclass or something? No.
        // It seems lines 90-95 are indeed unreachable with the current if/else structure.
        // I will proceed with the tests above which cover all reachable branches.
    }
}


