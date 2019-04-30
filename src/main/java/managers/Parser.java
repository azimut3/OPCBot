package managers;

import data.Port.Vessel;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import data.Port.PortContent;

public class Parser {
    public static boolean parsePort(){
        String blogUrl = "http://www.port.odessa.ua/ua/pro-port/pozitsiya-suden/2012-11-21-09-16-27";
        try {
            //TreeMap<String, List<Vessel>> portBerths = new TreeMap<>();
            List<Vessel> vesselsAtBerth = new ArrayList<>();
            Document doc = Jsoup.connect(blogUrl).get();
            Elements vesselsAtPortTable = doc.getElementsByClass("ships");
            //Element table = vesselsAtPortTable.select("table");
            Elements rows = vesselsAtPortTable.select("tr");
            for (int r = 1; r < rows.size(); r++) {
                Elements line = rows.get(r).select("td");
                if (line.get(0).text().length()>2) continue;
                String berth = line.get(0).text().replaceAll("\\D", "");
                vesselsAtBerth.add(new Vessel().setBerth(berth)
                        .setVesselName(line.get(1).text())
                        .setDate(line.get(2).text()));
                /*if (!portBerths.containsKey(berth)) {
                    List<Vessel> vessels = new ArrayList<>();
                    vessels.add(new Vessel().setBerth(berth)
                            .setVesselName(line.get(1).text())
                            .setDate(line.get(2).text()));
                    portBerths.put(berth, vessels);
                } else{
                    portBerths.get(berth).add(new Vessel().setBerth(berth)
                            .setVesselName(line.get(1).text())
                            .setDate(line.get(2).text()));
                }*/


            }
            PortContent.portBerths = vesselsAtBerth;
            return true;
        }
        catch (HttpStatusException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
