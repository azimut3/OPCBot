package data.Subscriprions;

import java.util.ArrayList;
import java.util.TreeMap;

public class Subs {
    private static Subs subberInstance;

    public static TreeMap<String, String> berthSubs = new TreeMap<>();
    public static TreeMap<String, String> berthChangeSubs = new TreeMap<>();
    public static ArrayList<String> weatherSubs = new ArrayList<>();

    public static TreeMap<String, ArrayList<String>> usersByBerths = new TreeMap<>();

    public static Subs getSubberInstance() {
        if (subberInstance == null) subberInstance = new Subs();
        return subberInstance;
    }

    public static void subscribeBerthsOnChanges(String user, String berths){
        if (berthChangeSubs.containsKey(user)) {
            for (String oldBerth : berthChangeSubs.get(user).split(" ")){
                usersByBerths.get(oldBerth).remove(user);
            }
            berthChangeSubs.replace(user, berths);
            for (String newBerth : berths.split(" ")){
                usersByBerths.get(newBerth).add(user);
            }
        } else {
            berthChangeSubs.put(user, berths);
            for (String newBerth : berths.split(" ")){
                if (!usersByBerths.containsKey(newBerth)) {
                    usersByBerths.put(newBerth, new ArrayList<String>());
                }
                usersByBerths.get(newBerth).add(user);
            }
        }
        System.out.println("user: " + user + " followed berths updates - " + berths);

    }

    public static void subscribeBerths(String user, String berths){
        if (berthSubs.containsKey(user)) {
            berthSubs.replace(user, berths);
        } else {
            berthSubs.put(user, berths);
        }
        System.out.println("user: " + user + " followed berths - " + berths);
    }

    public static void subscribeWeather(String user){
        if (weatherSubs.contains(user)) {
        } else {
            weatherSubs.add(user);
        }
    }



    public static void saveSubs() {

    }
}
