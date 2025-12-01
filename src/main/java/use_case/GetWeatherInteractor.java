package use_case;

import entities.Location;
import entities.WeatherData;
import entities.WeeklyData;

public class GetWeatherInteractor implements GetWeatherInputBoundary {

    private final WeatherDataGateway weatherGateway;
    private final GetWeatherOutputBoundary presenter;

    public GetWeatherInteractor(WeatherDataGateway weatherGateway,
                                GetWeatherOutputBoundary presenter) {
        this.weatherGateway = weatherGateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(GetWeatherInputData inputData) {

        try {
            // 1. Resolve the location first to get the correct country code (fixes "Always CA" bug)
            // The inputData might have a dummy country code (e.g. "CA") from the view.
            Location resolvedLocation = weatherGateway.searchLocation(inputData.getName());

            // 2. Fetch current weather using the RESOLVED location
            WeatherData data = weatherGateway.fetch(resolvedLocation);

            // 3. Fetch weekly forecast (UC4 integration)
            WeeklyData weeklyData = weatherGateway.fetchWeekly(resolvedLocation);

            GetWeatherOutputData output = new GetWeatherOutputData(
                    resolvedLocation.getName(),        // Use resolved city name
                    resolvedLocation.getCountryCode(), // Use resolved country code (e.g. "AE")
                    data.getTemperature(),
                    data.getFeelsLike(),
                    data.getHumidityPercentage(),
                    data.getWindSpeed(),
                    data.getCondition(),
                    data.isRaining(),
                    weeklyData,
                    ""
            );

            presenter.present(output);

        } catch (Exception e) {

            // FAILURE case
            GetWeatherOutputData output = new GetWeatherOutputData(
                    inputData.getName(),
                    inputData.getCountryCode(), // Fallback to input code on error
                    0,
                    0,
                    0,
                    0,
                    "",
                    false,
                    null,
                    "Could not find weather for that city."
            );

            presenter.present(output);
        }
    }
}
