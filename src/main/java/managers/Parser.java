package managers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import data.Port.PortContent;

public class Parser {
    public static void parsePort(){
        String blogUrl = "http://www.port.odessa.ua/ua/pro-port/pozitsiya-suden/2012-11-21-09-16-27";
        try {
            TreeMap<Integer, ArrayList<ArrayList<String>>> portBerths = new TreeMap<>();
            Document doc = Jsoup.connect(blogUrl).get();
            Elements vesselsAtPortTable = doc.getElementsByClass("ships");
            //Element table = vesselsAtPortTable.select("table");
            Elements rows = vesselsAtPortTable.select("tr");
            for (int r = 1; r < rows.size(); r++) {
                ArrayList<ArrayList<String>> vessels;
                ArrayList<String> vessel = new ArrayList<>();
                Elements line = rows.get(r).select("td");
                if (line.get(0).text().length()>2) continue;
                Integer berth = Integer.parseInt(line.get(0).text());
                if (!portBerths.containsKey(berth)) {
                    vessels = new ArrayList<>();
                    portBerths.put(berth, vessels);
                } else vessels = portBerths.get(berth);
                vessel.add(line.get(1).text());
                vessel.add(line.get(2).text());
                vessel.add(line.get(3).text());
                vessel.add(line.get(4).text());
                vessel.add(line.get(5).text());
                vessels.add(vessel);
                //Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы
                //PortContent.converLineToData(cols.text());
                //System.out.println(cols.text());
            }
            PortContent.portBerths = portBerths;
        }
        catch (HttpStatusException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
