package use_case;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the outfit recommendation use case.
 */
public class RecommendOutfitInteractor implements RecommendOutfitInputBoundary {

    private final RecommendOutfitOutputBoundary presenter;

    /**
     * Creates the interactor for recommending outfits.
     *
     * @param presenter the output boundary that presents results to the UI layer
     */
    public RecommendOutfitInteractor(RecommendOutfitOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Generates an outfit recommendation based on temperature, rain, and wind.
     *
     * @param inputData the weather data required to compute a recommendation
     */
    @Override
    public void execute(RecommendOutfitInputData inputData) {
        if (Double.isNaN(inputData.getTemperature())) {
            presenter.presentWeatherUnavailable("Weather unavailable, please refresh.");
            return;
        }

        double temperature = inputData.getTemperature();
        boolean raining = inputData.isRaining();
        double windSpeed = inputData.getWindSpeed();

        List<String> items = new ArrayList<>();
        StringBuilder rationale = new StringBuilder();

        if (temperature < 0) {
            items.add("top: Thermal base layer");
            items.add("mid-layer: Fleece sweater");
            items.add("outerwear: Heavy winter coat");
            items.add("pants: Insulated snow pants");
            items.add("footwear: Winter boots");
            items.add("accessories: Gloves, scarf, hat");
            rationale.append("Freezing (<0°C): extreme cold protection");
        }
        else if (temperature <= 5) {
            items.add("top: Thermal base layer");
            items.add("top: Sweater");
            items.add("outerwear: Puffer jacket");
            items.add("pants: Warm pants");
            items.add("footwear: Boots");
            rationale.append("Cold (≤5°C): heavy layers");
        }
        else if (temperature <= 15) {
            items.add("top: Long-sleeve shirt");
            items.add("outerwear: Light jacket");
            items.add("pants: Jeans");
            items.add("footwear: Sneakers");
            rationale.append("Cool (6–15°C): light jacket");
        }
        else if (temperature <= 25) {
            items.add("top: T-shirt");
            items.add("pants: Jeans or shorts");
            items.add("footwear: Sneakers");
            rationale.append("Mild (16–25°C): light clothing");
        }
        else {
            items.add("top: Breathable T-shirt");
            items.add("pants: Shorts");
            items.add("footwear: Sandals");
            rationale.append("Warm (≥26°C): minimal layers");
        }

        if (raining) {
            items.add("outerwear: Waterproof jacket");
            rationale.append(" + Rain: waterproof layer");
        }

        if (windSpeed >= 30) {
            items.add("outerwear: Windbreaker");
            rationale.append(" + Windy: add windbreaker");
        }

        RecommendOutfitOutputData outputData = new RecommendOutfitOutputData(
                "Recommended Outfit",
                items,
                rationale.toString()
        );

        presenter.present(outputData);
    }
}
