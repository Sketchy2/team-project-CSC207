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

        Location location = new Location(
                inputData.getName(),
                inputData.getCountryCode(),
                inputData.getLatitude(),
                inputData.getLongitude()
        );

        try {
            // Fetch current weather
            WeatherData data = weatherGateway.fetch(location);

            // Fetch weekly forecast (UC4 integration)
            WeeklyData weeklyData = weatherGateway.fetchWeekly(location);

            GetWeatherOutputData output = new GetWeatherOutputData(
                    inputData.getName(),               // cityName
                    inputData.getCountryCode(),        // countryCode
                    data.getTemperature(),             // temperature
                    data.getFeelsLike(),               // feelsLike
                    data.getHumidityPercentage(),      // humidity
                    data.getWindSpeed(),               // windSpeed
                    data.getCondition(),               // description
                    data.isRaining(),                  // isRaining
                    weeklyData,                        // weekly forecast
                    ""                                 // errorMessage = none
            );

            presenter.present(output);

        } catch (Exception e) {

            // FAILURE case: could not fetch weather (invalid city, API error, etc.)
            GetWeatherOutputData output = new GetWeatherOutputData(
                    inputData.getName(),
                    inputData.getCountryCode(),
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
