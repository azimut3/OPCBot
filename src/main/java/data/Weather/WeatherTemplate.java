package data.Weather;

public class WeatherTemplate {
    private String weatherState;
    private String temperature;
    private String pressure;
    private String humidity;
    private String windSpeed;
    private String clouds;

    public static final String WEATHER_STATE = "weatherState";
    public static final String SUNRISE = "rise";
    public static final String SUNSET = "set";
    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String WIND_SPEED = "windSpeed";
    public static final String CLOUDS = "clouds";
    public static final String VISIBILITY = "visibility";
    public static final String PRECIPITATION = "precipitation";
    public static final String DATE = "date";

    public String getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(String weatherState) {
        this.weatherState = weatherState;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }
}
