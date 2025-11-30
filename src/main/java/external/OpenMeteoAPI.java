package external;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import entities.Location;
import entities.WeatherData;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of WeatherService and WeatherDataGateway using the Open-Meteo APIs.
 */
public class OpenMeteoAPI implements WeatherService {

    private static final String GEOCODING_BASE =
            "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WEATHER_BASE =
            "https://api.open-meteo.com/v1/forecast";

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();


    @Override
    public Location searchLocation(String cityName) {
        String encoded = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        String url = GEOCODING_BASE
                + "?name=" + encoded
                + "&count=1"
                + "&language=en"
                + "&format=json";

        String body = sendGet(url);
        GeocodingResponse response = gson.fromJson(body, GeocodingResponse.class);

        if (response.results == null || response.results.length == 0) {
            throw new RuntimeException("No location found for: " + cityName);
        }

        GeoResult r = response.results[0];

        return new Location(
                r.name,
                r.countryCode,
                r.latitude,
                r.longitude
        );
    }
    private static String describeWeatherCode(int code) {
        // Based on WMO weather interpretation codes used by Open-Meteo :contentReference[oaicite:0]{index=0}
        switch (code) {
            case 0:
                return "Clear sky";
            case 1:
                return "Mainly clear";
            case 2:
                return "Partly cloudy";
            case 3:
                return "Overcast";

            case 45:
            case 48:
                return "Fog";

            case 51:
            case 53:
            case 55:
                return "Drizzle";

            case 56:
            case 57:
                return "Freezing drizzle";

            case 61:
            case 63:
            case 65:
                return "Rain";

            case 66:
            case 67:
                return "Freezing rain";

            case 71:
            case 73:
            case 75:
                return "Snowfall";

            case 77:
                return "Snow grains";

            case 80:
            case 81:
            case 82:
                return "Rain showers";

            case 85:
            case 86:
                return "Snow showers";

            case 95:
                return "Thunderstorm";

            case 96:
            case 99:
                return "Thunderstorm with hail";

            default:
                return "Unknown conditions";
        }
    }

    @Override
    public WeatherData getWeather(Location location) {
        String url = WEATHER_BASE
                + "?latitude=" + location.getLatitude()
                + "&longitude=" + location.getLongitude()
                + "&current=temperature_2m,apparent_temperature,"
                + "relative_humidity_2m,wind_speed_10m,precipitation,weather_code"
                + "&timezone=auto";

        String body = sendGet(url);
        WeatherResponse response = gson.fromJson(body, WeatherResponse.class);

        if (response.current == null) {
            throw new RuntimeException("No current weather data for: " + location.getName());
        }

        Current c = response.current;
        double temperature = c.temperature;
        double feelsLike = c.apparentTemperature;
        double humidity = c.relativeHumidity;
        double windSpeed = c.windSpeed;
        boolean isRaining = c.precipitation > 0.0;
        String countryCode = location.getCountryCode();

        String condition = describeWeatherCode(c.weatherCode);

        return new WeatherData(
                temperature,
                feelsLike,
                humidity,
                windSpeed,
                condition,
                isRaining,
                countryCode
        );
    }


    private String sendGet(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP " + response.statusCode()
                        + " for URL: " + url);
            }

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to call URL: " + url, e);
        }
    }


    private static class GeocodingResponse {
        GeoResult[] results;
    }

    private static class GeoResult {
        String name;
        double latitude;
        double longitude;
        @SerializedName("country_code")
        String countryCode;
    }

    private static class WeatherResponse {
        Current current;
    }

    private static class Current {
        String time;

        @SerializedName("temperature_2m")
        double temperature;

        @SerializedName("apparent_temperature")
        double apparentTemperature;

        @SerializedName("relative_humidity_2m")
        double relativeHumidity;

        @SerializedName("wind_speed_10m")
        double windSpeed;

        double precipitation;

        @SerializedName("weather_code")
        int weatherCode;
    }
}



