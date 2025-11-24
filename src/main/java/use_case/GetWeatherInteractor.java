package use_case;

import entities.Location;
import entities.WeatherData;

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
            WeatherData data = weatherGateway.fetch(location);

            GetWeatherOutputData output = new GetWeatherOutputData(
                    inputData.getName(),               // cityName
                    inputData.getCountryCode(),        // countryCode
                    data.getTemperature(),             // temperature
                    data.getFeelsLike(),               // feelsLike
                    data.getHumidityPercentage(),      // humidity
                    data.getWindSpeed(),               // windSpeed
                    data.getCondition(),               // description
                    data.isRaining(),                  // isRaining
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
                    "Could not find weather for that city."
            );

            presenter.present(output);
        }
    }
}
