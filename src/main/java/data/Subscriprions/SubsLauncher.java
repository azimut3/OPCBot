package data.Subscriprions;

public class SubsLauncher {
    public static void LaunchSubs(){
        Thread weatherSubTread = new WeatherSubThread("weatherSubTread");
        weatherSubTread.start();
        Thread berthSubThread = new BerthSubThread("berthSubThread");
        berthSubThread.start();
    }

    public synchronized static int getTime(int currentHours, int currentMinutes, int timeOfAllert){
        int time = 0;
        if (currentHours<timeOfAllert){
            time = 1000*60*60*(timeOfAllert - currentHours) - 1000*60*currentMinutes;
        } else {
            time = 1000*60*60*((24 - currentHours) + timeOfAllert) - 1000*60*currentMinutes;
        }


        return time;
    }

}
