package data.Weather;

public class CurrentWeather extends WeatherTemplate {
    private static CurrentWeather currentWeather;
    private String sunrise;
    private String sunset;
    private String visibility;

    private CurrentWeather() {
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public static CurrentWeather getCurrentWeather() {
        if (currentWeather == null) currentWeather = new CurrentWeather();
        return currentWeather;
    }

    //TODO написать этот класс
    public static void setCurrentWeather() {

    }
}
