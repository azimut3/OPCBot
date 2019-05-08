package data.Weather;

import managers.SecretData;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

public class WeatherUpdate {
    private static volatile TreeMap<String, String> currentWeather = null;

    private static final String URL_CURRENT = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String ID = "id=698740";
    private static final String API_KEY = "&APPID=" + SecretData.weatherKey;
    private static final String UNITS = "&units=metric";
    private static final String LANG = "&lang=ru";
    private static final String MODE = "&mode=xml";

    public static void updateCurrentWeather() {
        URL url = null;
        try {
            url = new URL(URL_CURRENT + ID + API_KEY + UNITS + LANG + MODE);
            currentWeather = StaxReader.parseCurrentWeather((InputStream) url.getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateForecastWeather() {
        URL url = null;
        try {
            url = new URL(URL_FORECAST + ID + API_KEY + UNITS + LANG + MODE);
            StaxReader.parseWeatherForecast((InputStream) url.getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object updateWeather(String source) {
        URL url = null;
        try {
            url = new URL(source + ID + API_KEY + UNITS + LANG + MODE);
            return StaxReader.parseCurrentWeather((InputStream) url.getContent());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentWeather() {
        StringBuilder weather = new StringBuilder();
        String uppChar = currentWeather.get(WeatherTemplate.WEATHER_STATE).substring(0, 1).toUpperCase();

        weather.append("Текущая погода:").append(System.lineSeparator())
                .append(uppChar + currentWeather.get(WeatherTemplate.WEATHER_STATE).substring(1)).append(",  ")
                .append(currentWeather.get("temperature")).append(" \u00B0" + "C, ")
                .append("облачность ")
                .append(currentWeather.get("cloudage")).append(" %,  ")
                .append("ветер - ").append(currentWeather.get("wind_speed")).append(" м/с.")
                .append(System.lineSeparator());
        weather.append("Видимость - ").append(currentWeather.get("visibility")).append(" м. ");
        weather.append("давление - ").append(currentWeather.get("pressure")).append(" hPa, ")
                .append("влажность - ").append(currentWeather.get("humidity")).append(" %.")
                .append(System.lineSeparator());
        return weather.toString();
    }
}
