package managers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class UkrCalendar {
    public static Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    static SimpleDateFormat formatHours = new SimpleDateFormat("H");
    static SimpleDateFormat formatMinutes = new SimpleDateFormat("m");
    static SimpleDateFormat formatDay = new SimpleDateFormat("d");



    {
        /*formatHours.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        formatMinutes.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        formatDay.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));*/

    }

    public static String getHours() {
        return String.valueOf(Integer.parseInt(formatHours.format(cal.getTime())) + 2);
    }

    public static String getMinutes() {
        return String.valueOf(Integer.parseInt(formatMinutes.format(cal.getTime())) + 2);
    }

    public static String getDay() {
        return String.valueOf(Integer.parseInt(formatDay.format(cal.getTime())) + 2);
    }
}
