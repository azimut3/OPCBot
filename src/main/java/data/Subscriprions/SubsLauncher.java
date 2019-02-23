package data.Subscriprions;

public class SubsLauncher {
    public static void LaunchSubs(){
        Thread weatherSubTread = new WeatherSubThread("weatherSubTread");
        weatherSubTread.start();
    }
}
