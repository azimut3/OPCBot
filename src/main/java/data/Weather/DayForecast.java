package data.Weather;

import managers.UkrCalendar;

public class DayForecast {
    private String day;
    private String month;
    private String weekDay;
    private String timeStart;
    private String timeEnd;
    private String temperature;
    private String humidity;
    private String pressure;
    private String windSpeed;
    private String cloudsCoverage;
    private String cloudsCoverageInPercents;
    private String precipitation;
    private String weatherState;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        String[] dayAndMonth = UkrCalendar.getDayAndMonth(day).split("\\.");
        this.day = dayAndMonth[0];
        this.month = dayAndMonth[1];
        setWeekDay(UkrCalendar.getWeekDay(day));
    }

    public String getWeekDay() {
        return weekDay;
    }

    private void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = UkrCalendar.getHoursAndMinutes(timeStart);
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = UkrCalendar.getHoursAndMinutes(timeEnd);
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudsCoverage() {
        return cloudsCoverage;
    }

    public void setCloudsCoverage(String cloudsCoverage) {
        this.cloudsCoverage = cloudsCoverage;
    }

    public String getCloudsCoverageInPercents() {
        return cloudsCoverageInPercents;
    }

    public void setCloudsCoverageInPercents(String cloudsCoverageInPercents) {
        this.cloudsCoverageInPercents = cloudsCoverageInPercents;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(String weatherState) {
        this.weatherState = weatherState;
    }

    public String getDate() {
    return String.format("%02d.%02d", Integer.parseInt(day), Integer.parseInt(month));
    }

}
