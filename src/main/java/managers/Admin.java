package managers;

import data.Subscriprions.Subs;

public class Admin {

    public static synchronized void newUser(String userId) {
        notifyAdmin("Новый пользователь зарегестрирован (" + userId + ")." +
                System.lineSeparator() +
                "Всего пользователей: " + Subs.users.size());
    }

    public static synchronized void notifyAdmin(String text) {
        OpcBot.getOpcBotInstance().sendMsg(
                OpcBot.getOpcBotInstance().createMsg(SecretData.admin), text);
    }

}
