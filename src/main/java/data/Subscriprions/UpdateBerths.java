package data.Subscriprions;

import data.Port.PortContent;
import managers.OpcBot;

import java.util.ArrayList;
import java.util.Set;

public class UpdateBerths {

    public static void compareResults(){
        if (PortContent.oldPortBerths != null) {
            Set<Integer> berths = PortContent.portBerths.keySet();
            Set<Integer> oldBerths = PortContent.oldPortBerths.keySet();
            StringBuilder missing = new StringBuilder();
            StringBuilder added = new StringBuilder();
            for (Integer s : berths) {
                if (!oldBerths.contains(s)) added.append(" ").append(s);
            }
            for (Integer s : oldBerths) {
                if (!berths.contains(s)) missing.append(" ").append(s);
            }
            if (missing.length() > 1) {
                for (String s : missing.toString().substring(1).split(" ")) {
                    ArrayList<String> usersArr = Subs.usersByBerths.get(s);
                    if (usersArr == null) continue;
                    if (usersArr.size() > 0) {
                        for (String vesselName : PortContent.oldPortBerths.get(s).get(0)) {
                            if (!PortContent.portBerths.get(s).get(0).contains(vesselName)) {
                                for (String user : usersArr) {
                                    UpdateBerths.notifyUsers(user, s, false, vesselName);
                                }
                            }
                        }
                    }
                }
            }
            if (added.length() > 1) {
                for (String s : added.toString().substring(1).split(" ")) {
                    ArrayList<String> usersArr = Subs.usersByBerths.get(s);
                    if (usersArr == null) continue;
                    if (usersArr.size() > 0) {
                        for (String vesselName : PortContent.oldPortBerths.get(s).get(0)) {
                            if (!PortContent.portBerths.get(s).get(0).contains(vesselName)) {
                                for (String user : usersArr) {
                                    UpdateBerths.notifyUsers(user, s, true, vesselName);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void notifyUsers(String chatId, String updatedBerth, boolean added, String vessel){

        StringBuilder builder = new StringBuilder();
        if (added){
            builder.append("Судно " + vessel + " пришвартовано на" + updatedBerth);
            builder.append(System.lineSeparator());
        } else {
            builder.append("Судно " + vessel + " отшвартовано с " + updatedBerth + " причала");
            builder.append(System.lineSeparator());
        }

        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                builder.toString());
    }
}
