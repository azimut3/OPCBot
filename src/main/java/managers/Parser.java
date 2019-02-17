package managers;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser {
    public String Parser(){
        String blogUrl = "http://www.port.odessa.ua/ua/pro-port/pozitsiya-suden/2012-11-21-09-16-27";
        try {
            Document doc = Jsoup.connect(blogUrl).get();
            Elements vesselsAtPortTable = doc.getElementsByClass("ships");
            //Element table = vesselsAtPortTable.select("table");
            Elements rows = vesselsAtPortTable.select("tr");
            StringBuilder builder = new StringBuilder();
            for (Element row : rows) {
                Elements cols = row.select("td");// разбиваем полученную строку по тегу  на столбы
                for (Element col : cols) {
                    builder.append(col.text()).append(" ");
                }
                builder.append(System.getProperty("line.separator"));
            }
            System.out.println(builder.toString());
            return builder.toString();
        }
        catch (HttpStatusException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
