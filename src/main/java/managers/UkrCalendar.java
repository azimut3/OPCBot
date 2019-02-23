package managers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class UkrCalendar {
    public static Calendar cal = Calendar.getInstance();
    static SimpleDateFormat formatHours = new SimpleDateFormat("H");
    static SimpleDateFormat formatMinutes = new SimpleDateFormat("m");
    static SimpleDateFormat formatDay = new SimpleDateFormat("d");



    {
        formatHours.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        formatMinutes.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        formatDay.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));

    }

    public static String getHours() {
        return formatHours.format(cal.getTime());
    }

    public static String getMinutes() {
        return formatMinutes.format(cal.getTime());
    }

    public static String getDay() {
        return formatDay.format(cal.getTime());
    }
}
