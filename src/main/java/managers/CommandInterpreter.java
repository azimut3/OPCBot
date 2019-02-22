package managers;

import data.Weather.WeatherForecast;
import data.WeatherUpdate;
import data.Port.PortContent;

public class CommandInterpreter {

    public static String processCommand(String command){
        switch (command){
        case "/port": return PortContent.getPortInfo();
        case "/currentweather": return WeatherUpdate.getCurrentWeather();
        case "/forecast": return WeatherForecast.getThreeDayForecast();
        case "/about": return about;
        case "/start": return start;

        }
        return "<pre>Invalid command</pre>";
    }

    static String about = "Данный бот разработан для получения справочной информации о наличии " +
            "судов в одесском порту, текущей погоде и почасовом прогнозе " +
            "погоды на 3 дня. Информация по порту берется с сайта ОМТП (http://www.port.odessa.ua)" +
            " и обновляется раз в 30 минут, а погода предоставляется благодаря погодному сервису " +
            "openweathermap.org. Текущая погода обновляется каждые 10 минут, а почасовой прогноз " +
            "каждые 3 часа. " + System.lineSeparator() +
            "Бот написан мной, Горчинским Игорем, email: i.horchynskyi@gmail.com.";

    static String start = "Добро пожаловать в <strong>OdessaPortCheck_bot</strong>(OPCbot)!"
            + System.lineSeparator() + System.lineSeparator() +
            "Данный бот разработан для получения справочной информации о наличии судов в" +
            " одесском порту, а так же информации о погоде";
}
