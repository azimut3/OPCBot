package managers;

import data.PortContent;

public class CommandInterpreter {

    public static String processCommand(String command){
        switch (command){
        case "/port": return PortContent.getPortInfo();
        }
        return "<pre>Invalid command</pre>";
    }
}
