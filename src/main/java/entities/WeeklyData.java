package entities;
import java.util.ArrayList;

public class WeeklyData {
    private ArrayList<DailyData> weeklyForecast;

    public ArrayList<DailyData> getWeek() {
        return weeklyForecast;
    }

    public void add(DailyData dailyData) {
        weeklyForecast.add(dailyData);
    }
}
