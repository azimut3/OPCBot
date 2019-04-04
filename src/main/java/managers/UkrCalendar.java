package managers;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class UkrCalendar {
    static SimpleDateFormat formatHours = new SimpleDateFormat("H");
    static SimpleDateFormat formatMinutes = new SimpleDateFormat("m");
    static SimpleDateFormat formatDay = new SimpleDateFormat("d");
    static SimpleDateFormat formatFullDate = new SimpleDateFormat("dd.MM.y");
    static ZonedDateTime zonedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Europe/Kiev"));

   /* static Calendar getCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }*/

    public static String getHours() {
        //return String.valueOf(Integer.parseInt(formatHours.format(getCalendar().getTime())) + 1);
        return String.valueOf(zonedDateTime.getHour());
    }

    public static String getMinutes() {
        //return String.valueOf(Integer.parseInt(formatMinutes.format(getCalendar().getTime())));
        return String.valueOf(zonedDateTime.getMinute());
    }

    public static String getDay() {
        //return String.valueOf(Integer.parseInt(formatDay.format(getCalendar().getTime())));
        return String.valueOf(zonedDateTime.getDayOfMonth());
    }

    public static String getFullDate() {
        StringBuilder builder = new StringBuilder();
        int day = zonedDateTime.getDayOfMonth();
        int month = zonedDateTime.getMonthValue();
        builder.append(day<10 ? "0" + day : day).append(".")
                .append(month<10 ? "0" + month : month).append(".")
                .append(zonedDateTime.getYear());
        //return formatFullDate.format(getCalendar().getTime());
        return builder.toString();
    }
}
