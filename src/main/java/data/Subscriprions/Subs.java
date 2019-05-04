package data.Subscriprions;

import data.DatabaseConnector.UserTableConnector;
import managers.OpcBot;
import managers.UkrCalendar;

import java.util.TreeMap;

public class Subs {
    private static Subs subberInstance;

    public static volatile TreeMap<String, User> users = new TreeMap<>();

    public static Subs getSubberInstance() {
        if (subberInstance == null) subberInstance = new Subs();
        return subberInstance;
    }

    public static synchronized void subscribeWeather(String user){
        boolean valueB = !Boolean.parseBoolean(users.get(user).getWeatherSubscription());
        String value = String.valueOf(valueB);
        UserTableConnector.editWeatherSub(user, value);
        users.get(user).setWeatherSubscription(value);
        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(user),
                "Вы " + (valueB ? "подписались на рассылку":"отписались от рассылки") + " погоды");
        System.out.println("user: " + user + " followed weather updates");
    }

    public static synchronized void setBerthsSelected(String user, String berths){
        users.get(user).setBerthsSelected(berths);
        UserTableConnector.editSelectedBerths(user, berths);
        System.out.println("user: " + user + " followed berths - " + berths);
    }

    public static synchronized void subscribeBerthsStatus(String user){
        boolean valueB = !Boolean.parseBoolean(users.get(user).getBerthStatusSubscription());
        String value = String.valueOf(valueB);
        UserTableConnector.editBerthSub(user, value);
        users.get(user).setBerthStatusSubscription(value);
        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(user),
                "Вы " + (valueB ? "подписались на рассылку сводки по причалам" :
                        "отписались от рассылки сводки по причалам"));
        System.out.println("user: " + user + " followed berths notifications");
    }

    public static synchronized void subscribeBerthsUpdate(String user){
        boolean valueB = !Boolean.parseBoolean(users.get(user).getBerthUpdateSubscription());
        String value = String.valueOf(valueB);
        UserTableConnector.editBerthUpdate(user, value);
        users.get(user).setBerthUpdateSubscription(value);
        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(user),
                "Вы " + (valueB ? "подписались на":"отписались от") +
                        " уведомления о статусе причалов");
        System.out.println("user: " + user + " followed berths updates");
    }

    public static String getBasicDateInfo(){
        return UkrCalendar.getCurrentFullDate();
    }



}
