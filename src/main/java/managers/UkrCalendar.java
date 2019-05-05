package managers;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UkrCalendar {
    private static ZonedDateTime zonedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Kiev"));

    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static String getCurrentHours() {
        return String.valueOf(zonedDateTime.getHour());
    }

    public static String getCurrentMinutes() {
        return String.valueOf(zonedDateTime.getMinute());
    }

    public static String getHoursAndMinutes(String date) {
        LocalTime localTime = LocalTime.parse(date, dateTimeFormat);
        int hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
        if (hour == 24) hour = 0;
        int minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
        return String.format("%02d:%02d", hour, minute);
    }

    public static String getHoursAndMinutes(String date, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime tempTime = LocalDateTime.parse(date, dateTimeFormatter);
        return String.valueOf(tempTime.getHour()).concat(":").concat(String.valueOf(tempTime.getMinute()));
    }

    public static String getHoursAndMinutes(ZonedDateTime zonedDateTime) {
        return String.valueOf(zonedDateTime.getHour()).concat(":").concat(String.valueOf(zonedDateTime.getMinute()));
    }

    public static String getCurrentDay() {
        return String.valueOf(zonedDateTime.getDayOfMonth());
    }

    public static String getDay(String date) {
        return getDay(date, getDateTimeFormater());
    }

    public static String getDay(ZonedDateTime zonedDateTime) {
        return String.valueOf(zonedDateTime.getDayOfMonth());
    }

    public static String getDay(String date, DateTimeFormatter dateTimeFormatter) {
        LocalDate definedDate = LocalDate.parse(date, dateTimeFormatter);
        return String.valueOf(definedDate.getDayOfMonth());
    }

    public static String getWeekDay(String date) {
        LocalDate definedDate = LocalDate.parse(date, getDateTimeFormater());
        return definedDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("ru"));
    }

    public static String getDayAndMonth(String date) {
        LocalDate definedDate = LocalDate.parse(date, getDateTimeFormater());
        return String.valueOf(definedDate.getDayOfMonth()).concat(".")
                .concat(String.valueOf(definedDate.getMonthValue()));
    }

    public static String getDayAndMonthAfterCurrent(int days) {
        return getHoursAndMinutes(zonedDateTime.plusDays(days));
    }

    public static ArrayList<String> getFiveDaysAndMonthAfterCurrent() {
    ArrayList<String> daysArray = new ArrayList<>();
        for (int i = 0; i<5; i++){
            daysArray.add(getDay(zonedDateTime.plusDays(i)));
        }
        return daysArray;
    }

    public static String getCurrentFullDate() {
        int day = zonedDateTime.getDayOfMonth();
        int month = zonedDateTime.getMonthValue();
        /*builder.append(day<10 ? "0" + day : day).append(".")
                .append(month<10 ? "0" + month : month).append(".")
                .append(zonedDateTime.getYear());*/
        return String.format("%02d.%02d.%d", day, month, zonedDateTime.getYear());
    }

    public static DateTimeFormatter getDateTimeFormater() {
        return dateTimeFormat;
    }
}
