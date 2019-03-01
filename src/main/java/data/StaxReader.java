package data;

import data.Weather.WeatherTemplate;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
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

    public static ArrayList<TreeMap<String, String>> parseWeatherForecast(InputStream file) {
        ArrayList<TreeMap<String, String>> weatherForecast = new ArrayList<>();
        TreeMap<String, String> forecast = new TreeMap<>();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(file);
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()){
                    StartElement startElement = xmlEvent.asStartElement();

                    if(startElement.getName().getLocalPart().equals("time")){
                        Attribute Attr = startElement.getAttributeByName(new QName("from"));
                        if(Attr != null){
                            forecast.put(WeatherTemplate.DATE, Attr.getValue());
                            //System.out.println(Attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("temperature")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            forecast.put(WeatherTemplate.TEMPERATURE, attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("humidity")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            forecast.put(WeatherTemplate.HUMIDITY, attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("pressure")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            forecast.put(WeatherTemplate.PRESSURE, attr.getValue());
                        }
                    }

                    // if(startElement.getName().getLocalPart().equals("wind")){
                    if(startElement.getName().getLocalPart().equals("windSpeed")) {
                        Attribute attr = startElement.getAttributeByName(new QName("mps"));
                        if (attr != null) {
                            forecast.put(WeatherTemplate.WIND_SPEED, attr.getValue());
                        }
                    }
                    //}

                    if(startElement.getName().getLocalPart().equals("clouds")){
                        Attribute attrDescr = startElement.getAttributeByName(new QName("value"));
                        if(attrDescr != null){
                            forecast.put(WeatherTemplate.CLOUDS, attrDescr.getValue());
                        }
                        //TODO добавить процент затученности
                        Attribute attr = startElement.getAttributeByName(new QName("all"));
                        if(attr != null){
                            forecast.put("clouds%", attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("precipitation")){
                        Attribute attr = startElement.getAttributeByName(new QName("value"));
                        if(attr != null){
                            forecast.put(WeatherTemplate.PRECIPITATION, attr.getValue());
                        }
                    }

                    if(startElement.getName().getLocalPart().equals("symbol")){
                        Attribute attr = startElement.getAttributeByName(new QName("name"));
                        if(attr != null){
                            forecast.put(WeatherTemplate.WEATHER_STATE, attr.getValue());
                        }
                    }

                }
                if(xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();
                    if(endElement.getName().getLocalPart().equals("time")){
                        weatherForecast.add(forecast);
                        forecast = new TreeMap<>();
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return weatherForecast;
    }
}
