package managers;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UkrCalendar {
    private volatile static ZonedDateTime zonedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Kiev"));

    private volatile static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private volatile static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public synchronized static String getCurrentHours() {
        return String.valueOf(zonedDateTime.getHour());
    }

    public synchronized static String getCurrentMinutes() {
        return String.valueOf(zonedDateTime.getMinute());
    }

    public synchronized static String getHoursAndMinutes(String date) {
        LocalTime localTime = LocalTime.parse(date, dateTimeFormat);
        int hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
        if (hour == 24) hour = 0;
        int minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
        return String.format("%02d:%02d", hour, minute);
    }

    public synchronized static String getHoursAndMinutes(String date, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime tempTime = LocalDateTime.parse(date, dateTimeFormatter);
        return String.valueOf(tempTime.getHour()).concat(":").concat(String.valueOf(tempTime.getMinute()));
    }

    public synchronized static String getHoursAndMinutes(ZonedDateTime zonedDateTime) {
        return String.valueOf(zonedDateTime.getHour()).concat(":").concat(String.valueOf(zonedDateTime.getMinute()));
    }

    public synchronized static String getCurrentDay() {
        return String.valueOf(zonedDateTime.getDayOfMonth());
    }

    public synchronized static String getDay(String date) {
        return getDay(date, getDateTimeFormater());
    }

    public synchronized static String getDay(ZonedDateTime zonedDateTime) {
        return String.valueOf(zonedDateTime.getDayOfMonth());
    }

    public synchronized static String getDay(String date, DateTimeFormatter dateTimeFormatter) {
        LocalDate definedDate = LocalDate.parse(date, dateTimeFormatter);
        return String.valueOf(definedDate.getDayOfMonth());
    }

    public synchronized static String getWeekDay(String date) {
        LocalDate definedDate = LocalDate.parse(date, getDateTimeFormater());
        return definedDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("ru"));
    }

    public synchronized static String getDayAndMonth(String date) {
        LocalDate definedDate = LocalDate.parse(date, getDateTimeFormater());
        return String.valueOf(definedDate.getDayOfMonth()).concat(".")
                .concat(String.valueOf(definedDate.getMonthValue()));
    }

    public synchronized static String getDayAndMonthAfterCurrent(int days) {
        return getHoursAndMinutes(zonedDateTime.plusDays(days));
    }

    public synchronized static ArrayList<String> getFiveDaysAndMonthAfterCurrent() {
    ArrayList<String> daysArray = new ArrayList<>();
        for (int i = 0; i<5; i++){
            daysArray.add(getDay(zonedDateTime.plusDays(i)));
        }
        return daysArray;
    }

   /* public synchronized static ArrayList<String> getFiveDaysAndMonth(int today) {
        ArrayList<String> daysArray = new ArrayList<>();
        for (int i = 0; i<5; i++){
            daysArray.add(getDay(zonedDateTime.plusDays(i)));
        }
        return daysArray;
    }*/

    public synchronized static String getCurrentFullDate() {
        int day = zonedDateTime.getDayOfMonth();
        int month = zonedDateTime.getMonthValue();
        /*builder.append(day<10 ? "0" + day : day).append(".")
                .append(month<10 ? "0" + month : month).append(".")
                .append(zonedDateTime.getYear());*/
        return String.format("%02d.%02d.%d", day, month, zonedDateTime.getYear());
    }

    public synchronized static DateTimeFormatter getDateTimeFormater() {
        return dateTimeFormat;
    }
}
