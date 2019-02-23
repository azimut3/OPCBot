package managers;

import data.Subscriprions.Subs;
import data.Weather.WeatherForecast;
import data.WeatherUpdate;
import data.Port.PortContent;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class CommandInterpreter {

    public static String processCommand(String command, SendMessage message){
        switch (command){
            case "/start":
                Keyboard.getMainKeyboard(message);
                OpcBot.getOpcBotInstance().sendMsg(message, start);
                break;
            case "/port":
                Keyboard.setPortKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, PortContent.getPortInfo());
                break;
            case "Порт":
                Keyboard.setPortKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, PortContent.getPortInfo());
                break;
            case "/currentweather":
                Keyboard.setWeatherKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, WeatherUpdate.getCurrentWeather());
                break;
            case "Погода":
                Keyboard.setWeatherKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, WeatherUpdate.getCurrentWeather());
                break;
            case "/forecast":
                Keyboard.setWeatherKeyboard(message, false);
                OpcBot.getOpcBotInstance().sendMsg(message, WeatherForecast.getThreeDayForecast());
                break;
            case "/about":
                OpcBot.getOpcBotInstance().sendMsg(message, about);
                break;
            case "О боте":
                OpcBot.getOpcBotInstance().sendMsg(message, about);
                break;
            case "Поделиться":
                Keyboard.setShareKeyboard(message, shareBody);
                OpcBot.getOpcBotInstance().sendMsg(message, shareHead + shareBody);
                break;
            case "/subscribePort":
                Keyboard.setPortKeyboard(message, false);
                OpcBot.getOpcBotInstance().sendMsg(message, followPort);
                break;
            case "/weatherSubscription":
                Keyboard.setWeatherSubscribeKeyboard(message);
                OpcBot.getOpcBotInstance().sendMsg(message, followWeather);
                break;
            case "/subscribeWeather":
                Subs.weatherSubs.add(message.getChatId());
                System.out.println(message.getChatId() + " подписался на рассылку погоды");
                OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance()
                                .createMsg(message.getChatId()),
                        "Вы подписались на рассылку погоды");
                break;

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

    static String start = "Добро пожаловать в <strong>OdessaPortCheck_bot</strong>(OPCbot)!" +
            System.lineSeparator() + System.lineSeparator() +
            "Данный бот разработан для получения справочной информации о наличии судов в" +
            " одесском порту, а так же информации о погоде";

    static String shareHead = "Для того чтобы поделиться ботом вы можете переслать это сообщение:" +
            System.lineSeparator() + System.lineSeparator();

    static String shareBody = "Привет! Я пользуюсь ботом (OdessaPortCheck_bot). Его можно " +
            "использовать чтобы следить за состоянием судов в одесском порту и отслеживать погоду."
            + System.lineSeparator() + System.lineSeparator() +
            "Попробуй и ты: t.me/OdessaPortCheck_bot";

    static String followPort = "Вы можете подписаться" +
            " на уведомления по изменению статуса причалов (судно пришвартовано/" +
            "отшвартовано - вам будет приходить уведомление) либо простое отслеживание, " +
            "уведомление по состоянию выбранных причалов два раза в сутки (5:30 и 17:30)." +
            System.lineSeparator() + "<i>(Можно выбрать оба варианта уведомления)</i>";

    static String followWeather = "Вы можете подписаться" +
            " на уведомления о погоде. В 17:00 вам будет приходить почасовой прогноз погоды на" +
            " ближайшие 3 дня, а в 7:00 почасовой прогноз на день.";
}
