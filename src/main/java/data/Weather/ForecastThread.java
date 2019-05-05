package data.Weather;

public class ForecastThread extends Thread{

    public ForecastThread(String name) {
    super(name);
}

    public void run(){
        while (true) {
            try {
                WeatherUpdate.updateForecastWeather();
                System.out.println("=== Weather forecast was updated ===");
                Thread.sleep(1000*60*60*3);
            } catch (InterruptedException e) {
                System.out.println("WeatherUpdate thread has been interrupted");
                e.printStackTrace();
            }

        }
    }

}
