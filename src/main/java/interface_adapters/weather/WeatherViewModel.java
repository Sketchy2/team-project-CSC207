package interface_adapters.weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel {

    // 1. Inner State class holds the actual data to be shown in the UI
    public static class State {
        public String cityName = "";
        public String temperatureText = "";
        public String feelsLikeText = "";
        public String humidityText = "";
        public String windSpeedText = "";
        public String descriptionText = "";
        public String errorMessage = "";

        // Raw data for other use cases (e.g. Recommendation)
        public double temperature = Double.NaN;
        public boolean isRaining = false;
        public double windSpeed = Double.NaN;

        // Weekly Forecast data for display
        public List<String> weeklyForecast = new ArrayList<>();
    }

    private State state = new State();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
