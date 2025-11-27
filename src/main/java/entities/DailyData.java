package entities;

public class DailyData {
    private final String date;
    private final double minTemp;
    private final double maxTemp;
    private final String condition;
    private final boolean precipitation;
    private final double wind;

    public DailyData(String date, double minTemp, double maxTemp, String condition, boolean precipitation, double wind) {
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.condition = condition;
        this.precipitation = precipitation;
        this.wind = wind;
    }

    public String getDate() {
        return date;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isPrecipitation() {
        return precipitation;
    }

    public double getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "date=" + date +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", condition='" + condition +
                ", precipitation=" + precipitation +
                ", wind=" + wind +
                '}';
    }
}
