package data.Subscriprions;

import data.Port.Vessel;
import managers.OpcBot;

import java.util.*;

public class UpdateBerths {

    public static synchronized TreeMap<String, List<Vessel>> getChangesOnPortUpdate(
        List<Vessel> oldOne, List<Vessel> newOne){

        TreeMap<String, List<Vessel>> changes = new TreeMap<>();
        for (Vessel vessel : oldOne) {
            if (!newOne.contains(vessel)) {
                vessel.setMoored(false);
                putInChangesMap(vessel, changes);
            }
        }
        for (Vessel vessel : newOne) {
            if (!oldOne.contains(vessel)) {
                vessel.setMoored(true);
                putInChangesMap(vessel, changes);
            }
        }
        return changes;
    }

    private static void putInChangesMap(Vessel vessel, TreeMap<String, List<Vessel>> changes) {
        if (changes.containsKey(vessel.getBerth())){
            changes.get(vessel.getBerth()).add(vessel);
        } else {
            ArrayList<Vessel> list = new ArrayList<>();
            list.add(vessel);
            changes.put(vessel.getBerth(), list);
        }
    }

    public static synchronized Set<String> sendInfoAboutBerths(TreeMap<String, User> users,
                                              TreeMap<String, List<Vessel>> changes,
                                              OpcBot bot) {
        Set<String> set = new HashSet<>();
        for (String user : users.keySet()){
            if (users.get(user).getBerthUpdateSubscription().equals("true")) {
                for (String berthNumber : changes.keySet()) {
                    if (users.get(user).getBerthsSelected().matches("^" + berthNumber +
                            "\\s.+|.+\\s" + berthNumber + "\\s.+|.+\\s" + berthNumber + "$|^" + berthNumber + "$")) {
                        for (Vessel vessel : changes.get(berthNumber)) {
                            set.add(notifyUsers(user, vessel, bot));
                        }
                    }
                }
            }
        }
        //System.out.println("set" + set);
        return set;
    }

    protected static String notifyUsers(String chatId, Vessel vessel, OpcBot bot){
        StringBuilder builder = new StringBuilder();
        if (vessel.isMoored()){
            builder.append("Судно " + vessel.getVesselName() + " пришвартовано на " + vessel.getBerth() +
                    " причал");
            builder.append(System.lineSeparator());
        } else {
            builder.append("Судно " + vessel.getVesselName() + " отшвартовано с " + vessel.getBerth() +
                    " причала");
            builder.append(System.lineSeparator());
        }
        //if (chatId == null) System.out.println(builder.toString());
        bot.sendMsg(bot.createMsg(chatId), builder.toString());
        return chatId.concat(" ").concat(vessel.getBerth()).concat(" ")
                .concat(vessel.getVesselName()).concat(" ")
                .concat(String.valueOf(vessel.isMoored()));
    }

}
