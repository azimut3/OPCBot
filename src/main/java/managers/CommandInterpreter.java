package managers;

public class CommandInterpreter {

    public static String processCommand(String command){
        switch (command){
        case "/port": return new Parser().Parser();
        }
        return null;
    }
}
