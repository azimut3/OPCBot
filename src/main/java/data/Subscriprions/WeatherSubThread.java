package data.Subscriprions;

import data.Weather.WeatherForecast;
import managers.OpcBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class WeatherSubThread extends Thread{

    public WeatherSubThread(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            boolean firstLaunch = true;
            try {
                if (firstLaunch){
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EET"));
                    SimpleDateFormat formatHours = new SimpleDateFormat("k");
                    SimpleDateFormat formatMinutes = new SimpleDateFormat("m");
                    int hours = Integer.parseInt(formatHours.format(cal.getTime()));
                    int minutes = Integer.parseInt(formatMinutes.format(cal.getTime()));
                    System.out.println("time " + hours + ":" + minutes);
                    if (minutes > 0) hours++;
                    if (hours < 7) {
                        System.out.println("До рассылки " + (7-hours) + ":" + (60 - minutes));
                        WeatherSubThread.sleep(1000*60*60*(7-hours) - 1000*60*(60 - minutes));
                        morningMessages();
                    } else if (hours > 7 && hours < 17) {
                        System.out.println("До рассылки " + (17-hours) + ":" + (60-minutes));
                        WeatherSubThread.sleep(1000*60*60*(17-hours) - 1000*60*(60-minutes));
                        eveningMessages();
                    } else {
                        System.out.println("До рассылки " + (24 - hours + 7) + ":" + (60-minutes));
                        WeatherSubThread.sleep(1000*60*60*(24 - hours + 7) - 1000*60*(60-minutes));
                        morningMessages();
                    }
                    firstLaunch = false;
                }
                WeatherSubThread.sleep(1000*60*60*10);
                morningMessages();
                System.out.println("=== Weather forecast for today was sent ===");
                WeatherSubThread.sleep(1000*60*10*14);
                eveningMessages();
                System.out.println("=== Weather forecast for 3 days was sent ===");

            } catch (InterruptedException e) {
                System.out.println("Port thread has been interrupted");
                e.printStackTrace();
            }

        }
    }

    private void morningMessages() {
        for (String chatId : Subs.weatherSubs) {
            try {
                WeatherSubThread.sleep(1000*60*60*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                    WeatherForecast.getTodaysForecast());
        }
    }

    private void eveningMessages() {
        for (String chatId : Subs.weatherSubs) {
            try {
                WeatherSubThread.sleep(1000*60*10*14);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                    WeatherForecast.getThreeDayForecast());
        }
    }
}
