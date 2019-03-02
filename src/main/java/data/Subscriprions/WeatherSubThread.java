package data.Subscriprions;

import data.Weather.WeatherForecast;
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
                    int hours = Integer.parseInt(UkrCalendar.getHours());
                    int minutes = Integer.parseInt(UkrCalendar.getMinutes());
                    System.out.println("Рассылка погоды осуществляется в " + timeMorning +
                            " и " + timeEvening);
                    if (hours < timeMorning) {
                        System.out.println("До утренней рассылки погоды " +
                                + (60*(timeMorning-hours) - minutes));
                        WeatherSubThread.sleep(1000*60*60*(timeMorning-hours) - 1000*60*(minutes));
                        morningMessages();
                        System.out.println("=== Weather forecast for today was sent(morning)* ===");
                        WeatherSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                        eveningMessages();
                        System.out.println("=== Weather forecast for 3 days was sent(evening)* ===");
                    } else if (hours > timeMorning && hours < timeEvening) {
                        System.out.println("До вечерней рассылки  погоды " +
                                + (60*(timeEvening-hours) - minutes));
                        WeatherSubThread.sleep(1000*60*60*(timeEvening-hours) - 1000*60*(minutes));
                        eveningMessages();
                        System.out.println("=== Weather forecast for 3 days was sent(evening)* ===");
                    } else {
                        //if (minutes > 0) hours++;
                        System.out.println("До утренней рассылки погоды " +
                                        (60*(24 - hours + timeMorning) - minutes));
                        WeatherSubThread.sleep(1000*60*60*((24 - hours) + timeMorning) -
                                 - 1000*60*(minutes));
                        morningMessages();
                        System.out.println("=== Weather forecast for today was sent(morning)* ===");
                        WeatherSubThread.sleep(1000*60*60*(timeEvening-timeMorning));
                        eveningMessages();
                        System.out.println("=== Weather forecast for 3 days was sent(evening)* ===");
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
            }

        }
    }

    private void morningMessages() {
        for (String chatId : Subs.users.keySet()) {
            if (Subs.users.get(chatId).get(0).equals("true")) {
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                        WeatherForecast.getTodaysForecast());
            }
        }
    }

    private void eveningMessages() {
        for (String chatId : Subs.users.keySet()) {
            if (Subs.users.get(chatId).get(0).equals("true")) {
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                        WeatherForecast.getThreeDayForecast());
            }
        }
    }
}
