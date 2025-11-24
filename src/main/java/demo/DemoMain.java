package demo;

import entities.Location;
import entities.WeatherData;
import external.OpenMeteoAPI;
import external.WeatherService;

import java.util.Scanner;

public class DemoMain {
    public static void main(String[] args) {
        WeatherService service = new OpenMeteoAPI();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a city: ");
        String city = scanner.nextLine();

        Location loc = service.searchLocation(city);
        WeatherData data = service.getWeather(loc);

        System.out.println(loc);
        System.out.println("Temperature: " + data.getTemperature());
        System.out.println("Feels like: " + data.getFeelsLike());
        System.out.println("Humidity: " + data.getHumidityPercentage());
        System.out.println("Wind: " + data.getWindSpeed());
        System.out.println("Condition: " + data.getCondition());
        System.out.println("Raining? " + data.isRaining());
    }
}

