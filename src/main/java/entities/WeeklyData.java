package entities;
import java.util.ArrayList;

public class WeeklyData {
    private ArrayList<DailyData> weeklyForecast;

    public WeeklyData() {
        this.weeklyForecast = new ArrayList<>();
    }

    public ArrayList<DailyData> getWeeklyForecast() {
        return weeklyForecast;
    }

    public void add(DailyData dailyData) {
        weeklyForecast.add(dailyData);
    }

    @Override
    public String toString() {
        return "WeeklyData{" + "weeklyForecast=" + weeklyForecast + '}';
    }
}
