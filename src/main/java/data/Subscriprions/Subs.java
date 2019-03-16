package data.Subscriprions;

import data.DatabaseConnector.JdbcConnector;
import managers.OpcBot;
import managers.UkrCalendar;

import java.util.ArrayList;
import java.util.TreeMap;

public class Subs {
    private static Subs subberInstance;

    public static volatile TreeMap<String, ArrayList<String>> users = new TreeMap<>();

    public static Subs getSubberInstance() {
        if (subberInstance == null) subberInstance = new Subs();
        return subberInstance;
    }

    public static synchronized void subscribeWeather(String user){
        boolean valueB = !Boolean.parseBoolean(users.get(user).get(0));
        String value = String.valueOf(valueB);
        JdbcConnector.editWeatherSub(user, value);
        users.get(user).set(0, value);
        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(user),
                "Вы " + (valueB ? "подписались на рассылку":"отписались от рассылки") + " погоды");
        System.out.println("user: " + user + " followed weather updates");
    }

    public static synchronized void setBerthSequence(String user, String berths){
        users.get(user).set(1, berths);
        JdbcConnector.editSelectedBerths(user, berths);
        System.out.println("user: " + user + " followed berths - " + berths);
    }

    public static synchronized void subscribeBerths(String user){
        boolean valueB = !Boolean.parseBoolean(users.get(user).get(2));
        String value = String.valueOf(valueB);
        JdbcConnector.editBerthSub(user, value);
        users.get(user).set(2, value);
        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(user),
                "Вы " + (valueB ? "подписались на":"отписались от") + " оповещения по причалам");
        System.out.println("user: " + user + " followed berths notifications");
    }

    public static synchronized void subscribeBerthsOnChanges(String user){
        boolean valueB = !Boolean.parseBoolean(users.get(user).get(3));
        String value = String.valueOf(valueB);
        JdbcConnector.editBerthUpdate(user, value);
        users.get(user).set(3, value);
        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(user),
                "Вы " + (valueB ? "подписались на":"отписались от") +
                        " уведомления о статусе по причалов");
        System.out.println("user: " + user + " followed berths updates");
    }

    public static String getBasicDateInfo(){
        return UkrCalendar.getFullDate();
    }

    public static String getStats() {
        StringBuilder builder = new StringBuilder();
        int usersNum = users.size();
        int weatherSubs = 0;
        int berthSubs = 0;
        int berthUpdate = 0;
        for (String user : users.keySet()){
            if (users.get(user).get(0).equals("true")) weatherSubs++;
            if (users.get(user).get(2).equals("true")) berthSubs++;
            if (users.get(user).get(3).equals("true")) berthUpdate++;
        }
        builder.append("Всего пользователей: ").append(usersNum)
                .append(System.lineSeparator())
                .append("Подписаны на погоду: ").append(weatherSubs).append("/").append(usersNum)
                .append(System.lineSeparator())
                .append("Подписаны на причалы: ").append(berthSubs).append("/").append(usersNum)
                .append(System.lineSeparator())
                .append("Подписаны на статус судов: ").append(berthUpdate).append("/")
                .append(usersNum)
                .append(System.lineSeparator());
        return builder.toString();
    }


}
