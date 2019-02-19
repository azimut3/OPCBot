package managers;

import data.CurrentWeather;

public class CurrentWeatherUpdate extends Thread{

    public CurrentWeatherUpdate(String name) {
        super(name);
    }

    public void run(){
        while (true) {
            try {
                CurrentWeather.updateWeather();
                System.out.println("=== Current weather was updated ===");
                Thread.sleep(1000*60*10);
            } catch (InterruptedException e) {
                System.out.println("Weather thread has been interrupted");
                e.printStackTrace();
            }

        }
    }
}