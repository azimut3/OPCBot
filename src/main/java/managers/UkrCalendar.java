package managers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class UkrCalendar {
    static SimpleDateFormat formatHours = new SimpleDateFormat("H");
    static SimpleDateFormat formatMinutes = new SimpleDateFormat("m");
    static SimpleDateFormat formatDay = new SimpleDateFormat("d");
    static SimpleDateFormat formatFullDate = new SimpleDateFormat("dd.MM.y");

    static Calendar getCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public static String getHours() {
        return String.valueOf(Integer.parseInt(formatHours.format(getCalendar().getTime())) + 2);
    }

    public static String getMinutes() {
        return String.valueOf(Integer.parseInt(formatMinutes.format(getCalendar().getTime())));
    }

    public static String getDay() {
        return String.valueOf(Integer.parseInt(formatDay.format(getCalendar().getTime())));
    }

    public static String getFullDate() {
        return formatFullDate.format(getCalendar().getTime());
    }
}
