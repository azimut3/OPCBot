package data.Port;

import javafx.util.Builder;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class PortContent {
    private static PortContent portContentInstance;
    public static TreeMap<Integer, ArrayList<ArrayList<String>>> portBerths = new TreeMap<>();
    private static ArrayList<String> berthArr = new ArrayList<>(), vesselArr = new ArrayList<>(),
            dateArr = new ArrayList<>(), agentArr = new ArrayList<>(),
            flagArr = new ArrayList<>(), codeArr = new ArrayList<>();
    private static final String BERTH = "Berth", VESSEL = "Vessel", DATE = "Date of call";
    private static int berthL = BERTH.length()+1, vesselL = VESSEL.length()+1,
            dateL = DATE.length()+1, agentL, flagL, codeL;

    private PortContent() {
    }

    public static void converLineToData(String line) {
        //System.out.println(line);
        String[] lineContent = line.split(" ");
        berthArr.add(lineContent[0]);
        berthL = Math.max(berthL, lineContent[0].length());
        vesselArr.add(lineContent[1]);
        vesselL = Math.max(vesselL, lineContent[1].length());
        dateArr.add(lineContent[2]);
        dateL = Math.max(dateL, lineContent[2].length());
        /*agentArr.add(lineContent[3]);
        agentL = Math.max(berthL, lineContent[3].length());
        flagArr.add(lineContent[4]);
        flagL = Math.max(berthL, lineContent[4].length());
        codeArr.add(lineContent[5]);
        codeL = Math.max(berthL, lineContent[5].length());*/
    }

    public static PortContent getPortContentInstance() {
        if (portContentInstance == null) portContentInstance = new PortContent();
        return portContentInstance;
    }

   /* public static String getPortInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append(" | ").append(formatString(BERTH, berthL)).append(" | ")
                .append(formatString(VESSEL, vesselL)).append(" | ")
                .append(formatString(DATE, dateL)).append(" |").append(System.lineSeparator());
        for (int i = 1; i < berthArr.size(); i++){
            builder.append(" | ").append(formatString(berthArr.get(i), berthL)).append(" | ")
                    .append(formatString(vesselArr.get(i), vesselL)).append(" | ")
                    .append(formatString(dateArr.get(i), dateL)).append(" |").append(System.lineSeparator());
        }
       // System.out.println(berthL + " " + vesselL + " " + dateL);
        return builder.toString();
    }*/

    public static String getPortInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append("<b>Список причалов и судов(дата швартовки):</b>").append(System.lineSeparator())
                .append(System.lineSeparator());
        Set<Integer> set = portBerths.keySet();
        for (Integer key : set){
            ArrayList<ArrayList<String>> vessels = portBerths.get(key);
            for (ArrayList<String> vessel : vessels) {
                builder.append(formatString(key.toString(), 2)).append(" | ").append(vessel.get(0))
                        .append(" <i>(").append(vessel.get(1)).append(")</i>")
                        .append(System.lineSeparator());
            }
        }
        return builder.toString();
    }

    private static String formatString(String s, int width){
        return String.format("%1$" + width + "s", s).replaceAll(" ", "  ");

    }

    public static void clearTable() {
        berthArr.clear();
        vesselArr.clear();
        dateArr.clear();
        agentArr.clear();
        flagArr.clear();
        codeArr.clear();
    }
}
