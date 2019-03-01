package data.Weather;

import data.WeatherUpdate;

public class CurrentWeatherThread extends Thread{

    public CurrentWeatherThread(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                WeatherUpdate.updateCurrentWeather();
                System.out.println("=== Current weather was updated ===");
                Thread.sleep(1000*60*10);
            } catch (InterruptedException e) {
                System.out.println("WeatherUpdate thread has been interrupted");
                e.printStackTrace();
            }

        }
    }
}