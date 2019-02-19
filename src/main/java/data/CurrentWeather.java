package data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TreeMap;

public class CurrentWeather {
    private String temp, windSpeed, pressure, humidity, weatherDescription,
        sunrise, sunset;
    private static TreeMap<String, String> currentWeather = null;

    public static void updateWeather() {
        String URL_SOURCE = "http://api.openweathermap.org/data/2.5/weather?";
        String ID = "id=698740";
        String API_KEY = "&APPID=fe901b4ce965424b15397e529b828d91";
        String UNITS = "&units=metric";
        String LANG = "&lang=ru";
        String MODE = "&mode=xml";
        // получение погоды с сайта
        URL url = null;
        try {
            url = new URL(URL_SOURCE + ID + API_KEY + UNITS + LANG + MODE);
            currentWeather = StaxReader.parseXML((InputStream) url.getContent());
            System.out.println(currentWeather);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentWeather() {
        StringBuilder weather = new StringBuilder();
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
        return weather.toString();
    }
}
