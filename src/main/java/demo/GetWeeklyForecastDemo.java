package demo;

import entities.Location;
import entities.WeeklyData;
import external.OpenMeteoAPI;
import external.WeatherService;

import java.util.Scanner;

public class GetWeeklyForecastDemo {
    public static void main(String[] args) {
        WeatherService service = new OpenMeteoAPI();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a city: ");
        String city = scanner.nextLine();

        Location loc = service.searchLocation(city);
        WeeklyData data = service.getWeeklyData(loc);

        System.out.println(loc);
        System.out.println("Temperature: " + data.getWeeklyForecast());
    }
}
