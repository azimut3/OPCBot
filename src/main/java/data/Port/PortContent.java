package data.Port;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class PortContent {
    private static PortContent portContentInstance;
    public static TreeMap<Integer, ArrayList<ArrayList<String>>> portBerths = new TreeMap<>();
    public static TreeMap<Integer, ArrayList<ArrayList<String>>> oldPortBerths = new TreeMap<>();

    private PortContent() {
    }

    public static PortContent getPortContentInstance() {
        if (portContentInstance == null) portContentInstance = new PortContent();
        return portContentInstance;
    }

    public static String getPortInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Список причалов и судов(дата швартовки):</b>").append(System.lineSeparator())
                .append(System.lineSeparator());
        if (portBerths.size()>=1) {
            Set<Integer> set = portBerths.keySet();
            for (Integer key : set) {
                builder.append(getBerthByNumber(key));
            }
        } else builder.append("К сожалению в данный момент нет доступа к данным с сайта одесского" +
                " порта(");
        return builder.toString();
    }

    public static String getBerthByNumber(Integer key) {
        StringBuilder builder = new StringBuilder();
        if (portBerths.containsKey(key)) {
            ArrayList<ArrayList<String>> vessels = portBerths.get(key);
            for (ArrayList<String> vessel : vessels) {
                builder.append(formatString(key.toString(), 2)).append(" | ").append(vessel.get(0))
                        .append(" <i>(").append(vessel.get(1)).append(")</i>")
                        .append(System.lineSeparator());
            }
        } else builder.append("");

        return builder.toString();
    }

    private static String formatString(String s, int width){
        return String.format("%1$" + width + "s", s).replaceAll(" ", "  ");

    }
}
