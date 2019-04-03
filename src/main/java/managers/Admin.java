package managers;

import data.DatabaseConnector.UserTableConnector;
import data.Subscriprions.Subs;

import java.util.ArrayList;

public class Admin {
    public static volatile ArrayList<String> stats = new ArrayList<>();

    public static synchronized void newUser(String userId, String name, String surname) {
        notifyAdmin("Новый пользователь зарегестрирован " + name + " " + surname +
                        " (" + userId + ")." +  System.lineSeparator() +
                "Всего пользователей: " + Subs.users.size());
    }

    public static synchronized void notifyAdmin(String text) {
        OpcBot.getOpcBotInstance().sendMsg(
                OpcBot.getOpcBotInstance().createMsg(SecretData.admin), text);
    }

    public static synchronized void sendMonthStats() {
        StringBuilder builder = new StringBuilder();
        builder.append("Статистика за месяц:").append(System.lineSeparator());
        builder.append("(всего, акт, просм, день)").append(System.lineSeparator());
        for (String stat : stats) {
            builder.append(stat).append(System.lineSeparator());
        }
        notifyAdmin(builder.toString());
    }

    public static void sendStats() {
        StringBuilder builder = new StringBuilder();
        int usersNum = Subs.users.size();
        int weatherSubs = 0;
        int berthSubs = 0;
        int berthUpdate = 0;
        for (String user : Subs.users.keySet()) {
            if (Subs.users.get(user).getWeatherSubscription().equals("true")) weatherSubs++;
            if (Subs.users.get(user).getBerthStatusSubscription().equals("true")) berthSubs++;
            if (Subs.users.get(user).getBerthUpdateSubscription().equals("true")) berthUpdate++;
        }
        String usersActiveStats = UserTableConnector.getUsersAndAvgCalls();
        builder.append("Зарегестрировано пользователей: ").append(usersNum)
                .append(System.lineSeparator())
                .append("Подписаны на погоду: ").append(weatherSubs).append("/").append(usersNum)
                .append(System.lineSeparator())
                .append("Подписаны на причалы: ").append(berthSubs).append("/").append(usersNum)
                .append(System.lineSeparator())
                .append("Подписаны на статус судов: ").append(berthUpdate).append("/")
                .append(usersNum)
                .append(System.lineSeparator())
                .append("Сегодня было активно пользователей (просмотров в среднем на пользователя) :")
                .append(usersActiveStats);
        notifyAdmin(builder.toString());
    }
}
