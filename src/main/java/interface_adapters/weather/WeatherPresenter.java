package interface_adapters.weather;

import use_case.GetWeatherOutputBoundary;
import use_case.GetWeatherOutputData;

public class WeatherPresenter implements GetWeatherOutputBoundary {

    private final WeatherViewModel viewModel;

    public WeatherPresenter(WeatherViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GetWeatherOutputData outputData) {
        WeatherViewModel.State state = viewModel.getState();

        // 1) Normalize city name casing
        String normalizedCity = toTitleCase(outputData.getCityName());

        // 2) Upper-case country code & attach to city
        String country = outputData.getCountryCode();
        if (country != null) {
            country = country.toUpperCase();
            state.cityName = normalizedCity + " (" + country + ")";
        } else {
            state.cityName = normalizedCity;
        }

        // 3) Fill weather fields
        state.temperatureText = outputData.getTemperature() + " °C";
        state.feelsLikeText = outputData.getFeelsLike() + " °C";
        state.humidityText = outputData.getHumidityPercentage() + "%";
        state.windSpeedText = outputData.getWindSpeed() + " m/s";
        state.descriptionText = outputData.getDescription();

        // 4) Error handling – pass through from OutputData
        state.errorMessage = outputData.getErrorMessage();

        if (!state.errorMessage.isEmpty()) {
            // Optional: clear numeric fields on error
            state.temperatureText = "";
            state.feelsLikeText = "";
            state.humidityText = "";
            state.windSpeedText = "";
            state.descriptionText = "";
        }

        viewModel.setState(state);
    }

    // Helper to turn "tORONTo" into "Toronto"
    private String toTitleCase(String raw) {
        if (raw == null || raw.isEmpty()) return "";
        String lower = raw.toLowerCase().trim();
        String[] parts = lower.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (p.isEmpty()) continue;
            sb.append(Character.toUpperCase(p.charAt(0)));
            if (p.length() > 1) {
                sb.append(p.substring(1));
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}


