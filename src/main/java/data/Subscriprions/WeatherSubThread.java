package data.Subscriprions;

import data.Weather.FiveDaysForecast;
import managers.Admin;
import managers.OpcBot;
import managers.UkrCalendar;

public class WeatherSubThread extends Thread{
    int timeMorning = 7;
    int timeEvening = 17;

    public WeatherSubThread(String name) {
        super(name);
    }

    public void run(){
        boolean firstLaunch = true;
        while (true) {
            try {
                if (firstLaunch){
                    int hours = Integer.parseInt(UkrCalendar.getCurrentHours());
                    int minutes = Integer.parseInt(UkrCalendar.getCurrentMinutes());
                    if (hours < timeMorning || hours > timeEvening){
                        BerthSubThread.sleep(SubsLauncher.getTime(hours, minutes, timeMorning));
                        morningMessages();
                        System.out.println("=== Weather forecast for today was sent(morning) ===");
                        BerthSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                        eveningMessages();
                        System.out.println("=== Weather forecast for 3 days was sent(evening) ===");
                    } else {
                        BerthSubThread.sleep(SubsLauncher.getTime(hours, minutes, timeEvening));
                        eveningMessages();
                        System.out.println("=== Weather forecast for 3 days was sent(evening) ===");
                    }
                    firstLaunch = false;
                }
                WeatherSubThread.sleep(1000*60*60*((24 - timeEvening) + timeMorning));
                morningMessages();
                System.out.println("=== Weather forecast for today was sent(morning) ===");
                WeatherSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                eveningMessages();
                System.out.println("=== Weather forecast for 3 days was sent(evening) ===");

            } catch (InterruptedException e) {
                System.out.println("Weather forecast thread has been interrupted");
                e.printStackTrace();
                Admin.notifyAdmin("Weather forecast thread has been interrupted");
                Admin.notifyAdmin(e.getMessage());
            }

        }
    }

    private void morningMessages() {
        for (String chatId : Subs.users.keySet()) {
            if (Subs.users.get(chatId).getWeatherSubscription().equals("true")) {
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                        FiveDaysForecast.getFiveDaysForecast().printTodaysForecast());
            }
        }
    }

    private void eveningMessages() {
        for (String chatId : Subs.users.keySet()) {
            if (Subs.users.get(chatId).getWeatherSubscription().equals("true")) {
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                        FiveDaysForecast.getFiveDaysForecast().printThreeDaysForecast());
            }
        }
    }
}
