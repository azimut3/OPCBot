package managers;

import data.CurrentWeather;
import data.PortContent;

public class CommandInterpreter {

    public static String processCommand(String command){
        switch (command){
        case "/port": return PortContent.getPortInfo();
        case "/currentweather": return CurrentWeather.getCurrentWeather();
        }
        return "<pre>Invalid command</pre>";
    }
}
