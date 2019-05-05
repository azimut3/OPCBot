package data.Weather;

import java.util.ArrayList;
import java.util.List;

public class FiveDaysForecast {
    private static FiveDaysForecast fiveDaysForecastInstance;

    private volatile List<DayForecast> firstDayForecast = new ArrayList<>();
    private volatile List<DayForecast> secondDayForecast = new ArrayList<>();
    private volatile List<DayForecast> thirdDayForecast = new ArrayList<>();
    private volatile List<DayForecast> fourthDayForecast = new ArrayList<>();
    private volatile List<DayForecast> fifthDayForecast = new ArrayList<>();

    private FiveDaysForecast(){
            }

    public synchronized void addDayForecast(DayForecast dayForecast, ArrayList<String> days){
        String today = days.get(0);
        String second = days.get(1);
        String third = days.get(2);
        String fourth = days.get(3);
        String fifth = days.get(4);

            if (dayForecast.getDay().equals(today)) {
                firstDayForecast.add(dayForecast);
            }
            else
                if (dayForecast.getDay().equals(second)) {
                    secondDayForecast.add(dayForecast);
                }
                else
                    if (dayForecast.getDay().equals(third)) {
                        thirdDayForecast.add(dayForecast);
                    }
                    else
                        if (dayForecast.getDay().equals(fourth)) {
                            fourthDayForecast.add(dayForecast);
                        }
                        else
                            if (dayForecast.getDay().equals(fifth)) {
                                fifthDayForecast.add(dayForecast);
                            }

    }

    public List<DayForecast> getFirstDayForecast() {
        return firstDayForecast;
    }

    public List<DayForecast> getSecondDayForecast() {
        return secondDayForecast;
    }

    public List<DayForecast> getThirdDayForecast() {
        return thirdDayForecast;
    }

    public List<DayForecast> getFourthDayForecast() {
        return fourthDayForecast;
    }

    public List<DayForecast> getFifthDayForecast() {
        return fifthDayForecast;
    }

    public String printTodaysForecast() {
        StringBuilder builder = new StringBuilder();
        builder.append("Прогноз погоды на сегодня:").append(System.lineSeparator())
                .append(System.lineSeparator());
        builder.append(printOneDayForecast(getFirstDayForecast())).append(System.lineSeparator());
        return builder.toString();
    }

    public String printThreeDaysForecast() {
        StringBuilder builder = new StringBuilder();
        builder.append("Прогноз погоды на 3 дня:").append(System.lineSeparator())
                .append(System.lineSeparator());
        builder.append(printOneDayForecast(getFirstDayForecast())).append(System.lineSeparator())
                .append(printOneDayForecast(getSecondDayForecast())).append(System.lineSeparator())
                .append(printOneDayForecast(getThirdDayForecast()));
        return builder.toString();
    }

    public String printFiveDaysForecast() {
        StringBuilder builder = new StringBuilder();
        builder.append("Прогноз погоды на 5 дней:").append(System.lineSeparator())
                .append(System.lineSeparator());
        builder.append(printOneDayForecast(getFirstDayForecast())).append(System.lineSeparator())
                .append(printOneDayForecast(getSecondDayForecast())).append(System.lineSeparator())
                .append(printOneDayForecast(getThirdDayForecast())).append(System.lineSeparator())
                .append(printOneDayForecast(getFourthDayForecast())).append(System.lineSeparator())
                .append(printOneDayForecast(getFifthDayForecast())).append(System.lineSeparator());
        return builder.toString();
    }

    private synchronized String printOneDayForecast(List<DayForecast> dayForecast) {
        StringBuilder builder = new StringBuilder();
        builder.append("<b>").append(dayForecast.get(0).getWeekDay()).append(". ")
                .append(dayForecast.get(0).getDate()).append("</b>: ")
                .append(System.lineSeparator());
        for (DayForecast forecast : dayForecast) {
            builder.append("[").append(forecast.getTimeStart()).append("-")
                    .append(forecast.getTimeEnd()).append("]: ")
                    .append(forecast.getWeatherState()).append(", ")
                    .append(Math.round(Double.parseDouble(forecast.getTemperature())))
                    .append(" \u00B0" + "C").append(System.lineSeparator());
            if (forecast.getPrecipitation() != null) {
                double precipitation = (double)Math.round(Double.parseDouble(forecast.getPrecipitation())
                        *100)/100;
                if (precipitation >= 0.01) {
                    builder.append("Осадки: ").append(precipitation).append(" мм")
                            .append(System.lineSeparator());
                }
            }
        }
        return builder.toString();
    }

    public synchronized static FiveDaysForecast getFiveDaysForecast(){
        if(fiveDaysForecastInstance == null) fiveDaysForecastInstance = new FiveDaysForecast();
        return fiveDaysForecastInstance;
    }

    private class ForecastDayException extends RuntimeException{
        public ForecastDayException(){

        }
    }

    public static FiveDaysForecast setUpNewForecast(){
        return fiveDaysForecastInstance = new FiveDaysForecast();
    }
}
