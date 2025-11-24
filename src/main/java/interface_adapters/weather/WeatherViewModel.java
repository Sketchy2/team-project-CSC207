package interface_adapters.weather;

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
    }

    private State state = new State();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
