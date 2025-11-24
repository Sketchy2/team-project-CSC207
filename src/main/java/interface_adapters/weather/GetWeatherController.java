package interface_adapters.weather;

import use_case.GetWeatherInputBoundary;
import use_case.GetWeatherInputData;

public class GetWeatherController {

    private final GetWeatherInputBoundary interactor;

    public GetWeatherController(GetWeatherInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * This method will be called by your Swing UI when the user clicks "Get Weather".
     */
    public void execute(String name,
                        String countryCode,
                        double latitude,
                        double longitude) {

        // Build the input data object for the use case
        GetWeatherInputData inputData = new GetWeatherInputData(
                name,
                countryCode,
                latitude,
                longitude
        );

        // Call the use case
        interactor.execute(inputData);
    }
}
