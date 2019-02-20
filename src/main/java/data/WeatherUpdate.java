package data;

import data.Weather.WeatherForecast;
import managers.SecretData;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TreeMap;

public class WeatherUpdate {
    private static TreeMap<String, String> currentWeather = null;

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
            //System.out.println(currentWeather);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //currentWeather = (TreeMap<String, String>)updateWeather(URL_CURRENT);
    }

    public static void updateForecastWeather() {
        URL url = null;
        try {
            url = new URL(URL_FORECAST + ID + API_KEY + UNITS + LANG + MODE);
            WeatherForecast.getWeatherForecastInstance().
                    setWeatherForecast(StaxReader
                            .parseWeatherForecast((InputStream) url.getContent()));
            //System.out.println(currentWeather);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* WeatherForecast.getWeatherForecastInstance().
                setWeatherForecast((ArrayList<TreeMap<String, String>>)updateWeather(URL_FORECAST));*/
    }

    public static Object updateWeather(String source) {
        URL url = null;
        try {
            url = new URL(source + ID + API_KEY + UNITS + LANG + MODE);
            return StaxReader.parseCurrentWeather((InputStream) url.getContent());
            //System.out.println(currentWeather);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentWeather() {
        StringBuilder weather = new StringBuilder();
        weather.append("<b>Текущая погода:</b>").append(System.lineSeparator())
                .append("Температура  ").append(currentWeather.get("temperature")).append(" \u00B0" + "C, ")
                .append("скорость ветра - ").append(currentWeather.get("wind_speed")).append(" м/с.")
                .append(System.lineSeparator());
        weather.append("Облачность - ").append(currentWeather.get("clouds")).append(", ")
                .append("видимость - ").append(currentWeather.get("visibility")).append(" м.")
                .append(System.lineSeparator());
        weather.append("Давление - ").append(currentWeather.get("pressure")).append(" hPa, ")
                .append("влажность - ").append(currentWeather.get("humidity")).append(" %.")
                .append(System.lineSeparator());
        return weather.toString();
    }

   /* public static String getWeatherForecast() {
        StringBuilder weather = new StringBuilder();
        //String[] time = "";
        for (TreeMap<String, String> map : weatherForecast) {
            weather.append("<b>Текущая погода:</b>").append(System.lineSeparator())
                    .append("Температура - ").append(currentWeather.get("temperature")).append(" \u00B0" + "C, ")
                    .append("скорость ветра - ").append(currentWeather.get("wind_speed")).append(" м/с.")
                    .append(System.lineSeparator());
            weather.append("Облачность - ").append(currentWeather.get("clouds")).append(", ")
                    .append("видимость - ").append(currentWeather.get("visibility")).append(" м.")
                    .append(System.lineSeparator());
            weather.append("Давление - ").append(currentWeather.get("pressure")).append(" hPa, ")
                    .append("влажность - ").append(currentWeather.get("humidity")).append(" %.")
                    .append(System.lineSeparator());
        }
        return weather.toString();
    }*/
}
