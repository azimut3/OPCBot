package data.Subscriprions;

import java.util.ArrayList;

public class Subs {
    private static Subs subberInstance;

    public static ArrayList<String> berthSubs = new ArrayList<>();
    public static ArrayList<String> berthChangeSubs = new ArrayList<>();
    public static ArrayList<String> weatherSubs = new ArrayList<>();

    public static Subs getSubberInstance() {
        if (subberInstance == null) subberInstance = new Subs();
        return subberInstance;
    }

    public static void subscribeBerthsOnChanges(String user){
        if (berthChangeSubs.contains(user)) {

        } else {
            berthChangeSubs.add(user);
        }
    }

    public static void subscribeBerths(String user){
        if (berthSubs.contains(user)) {

        } else {
            berthSubs.add(user);
        }
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
