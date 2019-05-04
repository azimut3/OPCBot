package data.Weather;

import managers.UkrCalendar;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.TreeMap;

public class StaxReader {

    public static TreeMap<String, String> parseCurrentWeather(InputStream file) {
        TreeMap<String, String> currentWeather = new TreeMap<>();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(file);
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();

                    if(startElement.getName().getLocalPart().equals("sun")){
                        Attribute riseAttr = startElement.getAttributeByName(new QName("rise"));
                        if(riseAttr != null){
                            currentWeather.put("rise", riseAttr.getValue());
                        }
                        Attribute setAttr = startElement.getAttributeByName(new QName("set"));
                        if(setAttr != null){
                            currentWeather.put("set", setAttr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("temperature")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            currentWeather.put("temperature", attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("humidity")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            currentWeather.put("humidity", attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("pressure")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            currentWeather.put("pressure", attr.getValue());
                        }
                    }

                   // if(startElement.getName().getLocalPart().equals("wind")){
                        if(startElement.getName().getLocalPart().equals("speed")) {
                            Attribute attr = startElement.getAttributeByName(new QName("value"));
                            if (attr != null) {
                                currentWeather.put("wind_speed", attr.getValue());
                            }
                        }
                    //}

                    if(startElement.getName().getLocalPart().equals("clouds")){
                        Attribute attr = startElement.getAttributeByName(new QName("name"));
                        if(attr != null){
                            currentWeather.put("clouds", attr.getValue());
                        }
                        Attribute attrValue = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            currentWeather.put("cloudage", attrValue.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("weather")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            currentWeather.put(WeatherTemplate.WEATHER_STATE, attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("visibility")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            currentWeather.put("visibility", attr.getValue());
                        }
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return currentWeather;
    }

    public static FiveDaysForecast parseWeatherForecast(InputStream file) {
        DayForecast dayForecast = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        FiveDaysForecast fiveDaysForecast = FiveDaysForecast.setUpNewForecast();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(file);
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();

                    if(startElement.getName().getLocalPart().equals("time")){
                        Attribute attrStart = startElement.getAttributeByName(new QName("from"));
                        Attribute attrEnd = startElement.getAttributeByName(new QName("to"));
                        if(attrStart != null){
                            dayForecast = new DayForecast();
                            dayForecast.setDay(attrStart.getValue());
                            dayForecast.setTimeStart(attrStart.getValue());
                        }
                        if(attrEnd != null){
                            dayForecast.setTimeEnd(attrEnd.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("temperature")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            dayForecast.setTemperature(attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("humidity")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            dayForecast.setHumidity(attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("pressure")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            dayForecast.setPressure(attr.getValue());
                        }
                    }

                    // if(startElement.getName().getLocalPart().equals("wind")){
                    if(startElement.getName().getLocalPart().equals("windSpeed")) {
                        Attribute attr = startElement.getAttributeByName(new QName("mps"));
                        if (attr != null) {
                            dayForecast.setWindSpeed(attr.getValue());
                        }
                    }
                    //}

                    if(startElement.getName().getLocalPart().equals("clouds")){
                        Attribute attrDescr = startElement.getAttributeByName(new QName("value"));
                        if(attrDescr != null){
                            dayForecast.setCloudsCoverage(attrDescr.getValue());
                        }
                        //TODO добавить процент затученности
                        Attribute attr = startElement.getAttributeByName(new QName("all"));
                        if(attr != null){
                            dayForecast.setCloudsCoverageInPercents(attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("precipitation")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            dayForecast.setPrecipitation(attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("symbol")){
                        Attribute attr = startElement.getAttributeByName(new QName("name"));
                        if(attr != null){
                            dayForecast.setWeatherState(attr.getValue());
                        }
                    }

                }
                if(xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();
                    if(endElement.getName().getLocalPart().equals("time")){
                        fiveDaysForecast.addDayForecast(dayForecast, UkrCalendar.getFiveDaysAndMonthAfterCurrent());
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return fiveDaysForecast;
    }
}
