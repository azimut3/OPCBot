package managers;

import data.DatabaseConnector.UserTableConnector;
import data.Subscriprions.Subs;
import data.Weather.FiveDaysForecast;
import data.Weather.WeatherUpdate;
import data.Port.PortContent;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class CommandInterpreter {

    public static String processCommand(String command, SendMessage message, Update update){
        UserTableConnector.updateCalls(String.valueOf(message.getChatId()));
        switch (command.toLowerCase()){
            case "/start":
                Keyboard.getMainKeyboard(message);
                UserTableConnector.addBasicUser(update);
                OpcBot.getOpcBotInstance().sendMsg(message, start);
                break;
            case "/port":
                Keyboard.setPortKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, PortContent.getPortInfo());
                break;
            case "порт":
                Keyboard.setPortKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, PortContent.getPortInfo());
                break;
            case "/currentweather":
                Keyboard.setWeatherKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, WeatherUpdate.getCurrentWeather());
                break;
            case "погода":
                Keyboard.setWeatherKeyboard(message, true);
                OpcBot.getOpcBotInstance().sendMsg(message, WeatherUpdate.getCurrentWeather());
                break;
            case "/forecast":
                Keyboard.setWeatherKeyboard(message, false);
                OpcBot.getOpcBotInstance().sendMsg(message,
                        FiveDaysForecast.getFiveDaysForecast().printThreeDaysForecast());
                break;
            case "/fivedaysforecast":
                Keyboard.setWeatherKeyboard(message, false);
                OpcBot.getOpcBotInstance().sendMsg(message,
                        FiveDaysForecast.getFiveDaysForecast().printFiveDaysForecast());
            break;
            case "/about":
                OpcBot.getOpcBotInstance().sendMsg(message, about);
                break;
            case "о боте":
                OpcBot.getOpcBotInstance().sendMsg(message, about);
                break;
            case "поделиться":
                Keyboard.setShareKeyboard(message, shareBody);
                OpcBot.getOpcBotInstance().sendMsg(message, shareHead + shareBody);
                break;
            case "/subscribeport":
                Keyboard.setPortKeyboard(message, false);
                StringBuilder builder = new StringBuilder();
                System.out.println(Subs.users.get(message.getChatId()));
                builder.append(followPort).append(System.lineSeparator())
                        .append(Subs.users.get(message.getChatId()).getBerthStatusSubscription()
                                .equals("true") ?
                                "[Вы уже подписаны на рассылку сводки]" :
                                "[Вы не подписаны на рассылку сводки]")
                        .append(System.lineSeparator())
                        .append(Subs.users.get(message.getChatId()).getBerthUpdateSubscription()
                                .equals("true") ?
                                "[Вы уже подписаны на рассылку уведомлений]" :
                                "[Вы не подписаны на рассылку уведомлений]").append(System.lineSeparator())
                        .append("[Выбраны причалы: {" + Subs.users.get(message.getChatId())
                                .getBerthsSelected())
                        .append("} ]");
                OpcBot.getOpcBotInstance().sendMsg(message, builder.toString());
                break;
            case "/weathersubscription":
                Keyboard.setWeatherSubscribeKeyboard(message);
                StringBuilder builderWeather = new StringBuilder();
                builderWeather.append(followWeather).append(System.lineSeparator());
                builderWeather.append(Subs.users.get(message.getChatId()).getWeatherSubscription()
                        .equals("true") ?
                        "[Вы уже подписаны на рассылку погоды]" :
                        "[Вы не подписаны на рассылку погоды]").append(System.lineSeparator());
                OpcBot.getOpcBotInstance().sendMsg(message, builderWeather.toString());
                break;
            case "/subscribeweather":
                Subs.subscribeWeather(message.getChatId());
                break;

            case "/sequenceofberths":
                OpcBot.getOpcBotInstance().sendMsg(message, berthSubInstruction);
                break;

            case "/subscribeberthsupdates":
                Subs.subscribeBerthsUpdate(message.getChatId());
                break;

            case "/subscribeberthsstatus":
                Subs.subscribeBerthsStatus(message.getChatId());
                break;

            case "stats":
                if (Subs.users.get(message.getChatId()).getStatus().equals("admin")){
                    Admin.sendStats();
                }
                break;
            case "month":
                if (Subs.users.get(message.getChatId()).getStatus().equals("admin")){
                    Admin.sendMonthStats();
                }
                break;
        }

        if (command.startsWith("bs ") || command.startsWith("bsu ")) {
            String berths = command.replaceAll("[^\\d\\s]", "").trim();
            if (berths.length() < 1) {
                OpcBot.getOpcBotInstance().sendMsg(message,
                        "<pre>Некорректный ввод</pre>");
            } else {
                Subs.setBerthsSelected(message.getChatId(), berths);
                OpcBot.getOpcBotInstance().sendMsg(message,
                    "Вы подписаны на [" + berths + "] причал(-ы)");
            }
        }

        if (command.startsWith("/announce ")){
            if (Subs.users.get(message.getChatId()).getStatus().equals("admin")) {
//                    OpcBot.getOpcBotInstance().sendMsg(message, Subs.getStats());
                Admin.notifyAll(command.replaceAll("^/announce", ""));
            }
        }

        if (command.startsWith("/reply ")) {
            Admin.notifyAdmin("(" + message.getChatId() + ")" +
                    update.getMessage().getChat().getFirstName() + " " +
                    update.getMessage().getChat().getLastName() + ": " +
                    command.replaceAll("^/reply", ""));
            OpcBot.getOpcBotInstance().sendMsg(message, "Ваше сообщение отправлено!");
        }

        if (command.startsWith("/replyto")) {
            if (Subs.users.get(message.getChatId()).getStatus().equals("admin")) {
                String toUser = command.replaceAll("(?:\\s\\d{9}\\s)", "").trim();
                OpcBot.getOpcBotInstance().sendMsg(
                        OpcBot.getOpcBotInstance().createMsg(toUser),
                        command.replaceAll("/replyto " + toUser, ""));
                OpcBot.getOpcBotInstance().sendMsg(message, "Ваше сообщение " + toUser + " отправлено!");
            }
        }

        return "<pre>Invalid command</pre>";
    }

    static String about = "Данный бот разработан для получения справочной информации о наличии " +
            "судов в одесском порту, текущей погоде и почасовом прогнозе " +
            "погоды на 3 дня. Информация по порту берется с сайта ОМТП (http://www.port.odessa.ua)" +
            " и обновляется раз в 15 минут, а погода предоставляется благодаря погодному сервису " +
            "openweathermap.org. Текущая погода обновляется каждые 10 минут, а почасовой прогноз " +
            "каждые 3 часа. " + System.lineSeparator() +
            "Бот написан Горчинским Игорем, email: i.horchynskyi@gmail.com.";

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
            " на отслеживание статуса причалов (судно пришвартовано/" +
            "отшвартовано - вам будет приходить уведомление) либо на сводку по причалам, " +
            "сводка по выбранным причалов два раза в сутки (5:00 и 17:00)." +
            System.lineSeparator() + "<i>(Можно выбрать оба варианта уведомления)</i>";

    static String followWeather = "Вы можете подписаться" +
            " на уведомления о погоде. В 17:00 вам будет приходить почасовой прогноз погоды на" +
            " ближайшие 3 дня, а в 7:00 почасовой прогноз на день.";

    static String berthSubInstruction = "Введите команду 'bs' и номера причалов на которые вы хотите подписаться" +
            " через пробел. Пример:" + System.lineSeparator() + "bs 7 8 28 14";

    /*static String berthUpdateInstruction = "Введите команду 'bsu' и номера причалов на которые вы хотите подписаться" +
            " через пробел. Пример:" + System.lineSeparator() + "bsu 7 8 28 14";*/
}
