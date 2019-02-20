package data.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherForecast {
    private static WeatherForecast weatherForecastInstance;

    private static ArrayList<ForecastWeather> todayForecast = new ArrayList<>();
    private static ArrayList<ForecastWeather> tomorrowForecast = new ArrayList<>();
    private static ArrayList<ForecastWeather> afterTomorrowForecast = new ArrayList<>();

    private WeatherForecast(){}

    public static WeatherForecast getWeatherForecastInstance() {
        if (weatherForecastInstance == null) weatherForecastInstance = new WeatherForecast();
        return weatherForecastInstance;
    }

    public void setWeatherForecast(ArrayList<TreeMap<String, String>> forecast){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EET"));
        SimpleDateFormat formatDay = new SimpleDateFormat("d");
        todayForecast.clear();
        tomorrowForecast.clear();
        afterTomorrowForecast.clear();
        int dateNum = 0;
        for (TreeMap<String, String> weather : forecast) {
            //System.out.println(weather);
            ForecastWeather forecastWeather = new ForecastWeather();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            try {
                Calendar calendar = new GregorianCalendar();
                //System.out.println(weather.get(WeatherTemplate.DATE));
                calendar.setTime(dateFormat.parse(weather.get(WeatherTemplate.DATE)));
                dateNum = Integer.parseInt(formatDay.format(calendar.getTime())) -
                        Integer.parseInt(formatDay.format(cal.getTime()));
                //System.out.println(dateNum + " num");
                forecastWeather.setDay(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            forecastWeather.setWeatherState(weather.get(WeatherTemplate.WEATHER_STATE));
            forecastWeather.setTemperature(weather.get(WeatherTemplate.TEMPERATURE));
            forecastWeather.setHumidity(weather.get(WeatherTemplate.HUMIDITY));
            forecastWeather.setPressure(weather.get(WeatherTemplate.PRESSURE));
            forecastWeather.setWindSpeed(weather.get(WeatherTemplate.WIND_SPEED));
            forecastWeather.setClouds(weather.get(WeatherTemplate.CLOUDS));
            if (weather.containsKey(WeatherTemplate.PRECIPITATION))
            forecastWeather.setPrecipitation(weather.get(WeatherTemplate.PRECIPITATION));
            if (dateNum == 0) todayForecast.add(forecastWeather);
            else if (dateNum == 1) tomorrowForecast.add(forecastWeather);
            else if (dateNum == 2) afterTomorrowForecast.add(forecastWeather);
        }
    }

    public static String getThreeDayForecast() {
        StringBuilder builder = new StringBuilder();
        builder.append("Прогноз погоды на 3 дня:").append(System.lineSeparator())
                .append(System.lineSeparator());
        builder.append(getForecast(todayForecast)).append(System.lineSeparator())
                .append(getForecast(tomorrowForecast)).append(System.lineSeparator())
                .append(getForecast(afterTomorrowForecast));
        return builder.toString();
    }

    private static String getForecast(ArrayList<ForecastWeather> forecast) {
        StringBuilder builder = new StringBuilder();
        for (ForecastWeather weather : forecast) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM' ('HH:mm')'");
            builder.append(dateFormat.format(weather.getDay().getTime())).append(": ")
                    .append(weather.getWeatherState()).append(", " + weather.getTemperature())
                    .append(" \u00B0" + "C").append(System.lineSeparator());
            if (weather.getPrecipitation() != null) {
                builder.append("Осадки: ").append(weather.getPrecipitation())
                        .append("%").append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    private class ForecastWeather extends WeatherTemplate {
        private Calendar day;
        private String precipitation;

        public Calendar getDay() {
            return day;
        }

        public void setDay(Calendar day) {
            this.day = day;
        }

        public String getPrecipitation() {
            return precipitation;
        }

        public void setPrecipitation(String precipitation) {
            this.precipitation = precipitation;
        }


    }
}
