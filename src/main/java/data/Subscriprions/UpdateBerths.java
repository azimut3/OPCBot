package data.Subscriprions;

import data.Port.PortContent;
import managers.OpcBot;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class UpdateBerths {
    private static TreeMap<String, ArrayList<ArrayList<String>>> changes = new TreeMap();

    public static void compareResults(){

        if (PortContent.oldPortBerths != null) {
            Set<Integer> newBerths = PortContent.portBerths.keySet();
            Set<Integer> oldBerths = PortContent.oldPortBerths.keySet();
            for (Integer s : newBerths) {
                if (!oldBerths.contains(s)) {
                    for (ArrayList<String> vesselsArr : PortContent.portBerths.get(s))
                        putInChangesMap(String.valueOf(s), vesselsArr.get(0), "+");
                }
                if (oldBerths.contains(s)){
                    PortContent.portBerths.get(s);
                }
            }




            for (Integer s : oldBerths) {
                //if (!newBerths.contains(s)) missing.append(" ").append(s);
            }


        }
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
