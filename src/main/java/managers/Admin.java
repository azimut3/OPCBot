package managers;

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
}
