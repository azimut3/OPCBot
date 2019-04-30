package data.Port;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class PortContent {
    private static PortContent portContentInstance;
    public static volatile List<Vessel> portBerths = new ArrayList<>();
    public static volatile List<Vessel> oldPortBerths = new ArrayList<>();

    private PortContent() {
    }

    public static PortContent getPortContentInstance() {
        if (portContentInstance == null) portContentInstance = new PortContent();
        return portContentInstance;
    }

    public static String getPortInfo(){
        TreeMap<String, List<Vessel>> vessels = getBerthMap(portBerths);
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Список причалов и судов(дата швартовки):</b>").append(System.lineSeparator())
                .append(System.lineSeparator());
        if (vessels.size()>=1) {
            Set<String> set = vessels.keySet();
            for (String key : set) {
                builder.append(getBerthByNumber(key));
            }
        } else builder.append("К сожалению в данный момент нет доступа к данным с сайта одесского" +
                " порта(");
        return builder.toString();
    }

    /*public static String getBerthByNumber(String key) {
        StringBuilder builder = new StringBuilder();
        if (portBerths.containsKey(key)) {
            ArrayList<Vessel> vessels = portBerths.get(key);
            for (Vessel vessel : vessels) {
                builder.append(formatString(key, 2)).append(" | ").append(vessel.getVesselName())
                        .append(" <i>(").append(vessel.getDate()).append(")</i>")
                        .append(System.lineSeparator());
            }
        } else builder.append("");

        return builder.toString();
    }*/

    public static String getBerthByNumber(String key) {
        StringBuilder builder = new StringBuilder();
        for (Vessel vessel : portBerths) {
            if (vessel.getBerth().equals(key)) {
                builder.append(formatString(key, 2)).append(" | ").append(vessel.getVesselName())
                        .append(" <i>(").append(vessel.getDate()).append(")</i>")
                        .append(System.lineSeparator());
            } else builder.append("");
        }

        return builder.toString();
    }

    private static TreeMap<String, List<Vessel>> getBerthMap(List<Vessel> list){
        TreeMap<String, List<Vessel>> map = new TreeMap<>();
        for(Vessel vessel : list){
            if(map.containsKey(vessel.getBerth())){
                map.get(vessel.getBerth()).add(vessel);
            } else{
                List<Vessel> vessels = new ArrayList<>();
                vessels.add(vessel);
                map.put(vessel.getBerth(), vessels);
            }
        }
        return map;
    }

    private static String formatString(String s, int width){
        return String.format("%1$" + width + "s", s).replaceAll(" ", "  ");

    }
}
