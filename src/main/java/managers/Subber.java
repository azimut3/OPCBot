package managers;

import java.util.ArrayList;
import java.util.TreeMap;

public class Subber {
    private static Subber subberInstance;
    public static TreeMap<String, ArrayList<String>> subscribes = new TreeMap<>();

    public static Subber getSubberInstance() {
        if (subberInstance == null) subberInstance = new Subber();
        return subberInstance;
    }
}
