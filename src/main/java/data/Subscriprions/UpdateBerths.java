package data.Subscriprions;

import data.Port.PortContent;
import managers.OpcBot;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class UpdateBerths {
    private static TreeMap<String, ArrayList<ArrayList<String>>> changes = new TreeMap<>();

    public static void compareResults(){

        if (PortContent.oldPortBerths.size() > 1) {
            Set<Integer> newBerths = PortContent.portBerths.keySet();
            Set<Integer> oldBerths = PortContent.oldPortBerths.keySet();
            for (Integer s : newBerths) {
                if (!oldBerths.contains(s)) {
                    for (ArrayList<String> vesselsArr : PortContent.portBerths.get(s))
                        putInChangesMap(String.valueOf(s), vesselsArr.get(0), "+");
                } else {
                    for (ArrayList<String> arr : PortContent.portBerths.get(s)){
                        boolean put = true;
                        for (ArrayList<String> arrOld : PortContent.oldPortBerths.get(s)){
                            if (arrOld.contains(arr.get(0))) put = false;
                        }
                        if (put) putInChangesMap(String.valueOf(s), arr.get(0), "+");
                    }
                }
            }

            for (Integer s : oldBerths) {
                if (!newBerths.contains(s)) {
                    for (ArrayList<String> vesselsArr : PortContent.oldPortBerths.get(s))
                        putInChangesMap(String.valueOf(s), vesselsArr.get(0), "-");
                } else {
                    for (ArrayList<String> arr : PortContent.oldPortBerths.get(s)){
                        boolean put = true;
                        for (ArrayList<String> arrNew : PortContent.portBerths.get(s)){
                            if (arrNew.contains(arr.get(0))) put = false;
                        }
                        if (put) putInChangesMap(String.valueOf(s), arr.get(0), "-");
                    }
                }
            }
        }

        whoToSend();
    }

    private static void putInChangesMap(String berth, String vesselName, String change) {
        if (!changes.containsKey(berth)){
            ArrayList<ArrayList<String>> vesselsMoored = new ArrayList<>();
            changes.put(berth, vesselsMoored);
        }
        ArrayList<String> vessel = new ArrayList<>();
        vessel.add(vesselName);
        vessel.add(change);
        changes.get(berth).add(vessel);
    }

    private static void whoToSend() {
        for (String user : Subs.users.keySet()){
            for (String berth : changes.keySet()) {
                if (Subs.users.get(user).get(1).contains(berth + " ")) {
                    for (ArrayList<String> arr: changes.get(berth)) {
                        boolean changeValue = arr.get(1).equals("+");
                        notifyUsers(user, berth, changeValue, arr.get(0));
                    }
                }
            }
        }
    }

    public static void notifyUsers(String chatId, String updatedBerth, boolean added, String vessel){

       /* StringBuilder builder = new StringBuilder();
        if (added){
            builder.append("Судно " + vessel + " пришвартовано на " + updatedBerth + " причал");
            builder.append(System.lineSeparator());
        } else {
            builder.append("Судно " + vessel + " отшвартовано с " + updatedBerth + " причала");
            builder.append(System.lineSeparator());
        }

        OpcBot.getOpcBotInstance().sendMsg(OpcBot.getOpcBotInstance().createMsg(chatId),
                builder.toString());*/
    }
}
