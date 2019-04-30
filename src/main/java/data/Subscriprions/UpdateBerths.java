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
        System.out.println("map" + set);
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

    /*public static void whoToSendToConsole() {
        System.out.println(changes);
        for (String berth : changes.keySet()) {
            for (ArrayList<String> arr: changes.get(berth)) {
                boolean changeValue = arr.get(1).equals("+");
                notifyUsers(null, berth, changeValue, arr.get(0));
            }
        }
    }*/
    /*private static void sendInfoAboutBerths() {
        for (String user : Subs.users.keySet()){
            if (Subs.users.get(user).getBerthUpdateSubscription().equals("true")) {
                for (String berth : changes.keySet()) {
                    if (Subs.users.get(user).getBerthsSelected().contains(berth + " ")) {
                        for (ArrayList<String> arr : changes.get(berth)) {
                            boolean changeValue = arr.get(1).equals("+");
                            notifyUsers(user, berth, changeValue, arr.get(0));
                        }
                    }
                }
            }
        }
    }*/
    /* public static synchronized void getChangesOnPortUpdate(TreeMap<Integer, ArrayList<Vessel>> oldOne,
                        TreeMap<Integer, ArrayList<Vessel>> newOne){
         changes = new TreeMap<>();
         if (oldOne.size() > 0) {
             Set<Integer> newBerths = newOne.keySet();
             Set<Integer> oldBerths = oldOne.keySet();
             for (Integer newBerthNum : newBerths) {
                 if (!oldBerths.contains(newBerthNum)) {
                     for (Vessel vessel : newOne.get(newBerthNum))
                         putInChangesMap(String.valueOf(newBerthNum), vessel.getVesselName(), "+");
                 } else {
                     for (Vessel vesselNew : newOne.get(newBerthNum)){
                         boolean put = true;
                         for (Vessel vesselOld : oldOne.get(newBerthNum)){
                             if (vesselOld.equals(vesselNew)) put = false;
                         }
                         if (put) putInChangesMap(String.valueOf(newBerthNum), vesselNew.getVesselName(), "+");
                     }
                 }
             }

             for (Integer s : oldBerths) {
                 if (!newBerths.contains(s)) {
                     for (Vessel vessel : oldOne.get(s))
                         putInChangesMap(String.valueOf(s), vessel.getVesselName(), "-");
                 } else {
                     for (Vessel vesselOld : oldOne.get(s)){
                         boolean put = true;
                         for (Vessel vesselNew : newOne.get(s)){
                             if (vesselNew.equals(vesselOld)) put = false;
                         }
                         if (put) putInChangesMap(String.valueOf(s),  vesselOld.getVesselName(), "-");
                     }
                 }
             }
         }

         sendInfoAboutBerths();
     }

     private static void putInChangesMap(String berth, String vesselName, String change) {
         if (!changes.containsKey(berth)){
             ArrayList<Vessel> vesselsMoored = new ArrayList<>();
             vesselsMoored.add(new Vessel().setVesselName(vesselName).setBerth(berth));
             changes.put(berth, vesselsMoored);
         }
         ArrayList<String> vessel = new ArrayList<>();
         vessel.add(vesselName);
         vessel.add(change);
         changes.get(berth).add(vessel);
     }*/
}
