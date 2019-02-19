import managers.CurrentWeatherUpdate;
import managers.PortUpdate;
import data.CurrentWeather;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


public class OdessaPortCheckApplication {

    public static void main(String[] args) {
        System.out.println("OPCBot: starting...");
       /* Properties botInfo = new Properties();
        try {
            FileInputStream fis = new FileInputStream(OdessaPortCheckApplication.class
                    .getResource("bot.properties").getFile());
            botInfo.load(fis);

            OPCBot.setBotToken(botInfo.getProperty("botToken"));
            OPCBot.setBotUsername(botInfo.getProperty("botName"));

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Thread portUpdateThread = new PortUpdate("portUpdateThread");
        portUpdateThread.start();
        Thread currentWeatherThread = new CurrentWeatherUpdate("currentWeatherThread");
        currentWeatherThread.start();
        OPCBot.setBotToken("711608450:AAGcmLVZLBskemrf88PFKJ5b_3j8ATQl7yg");
        OPCBot.setBotUsername("OPCbot");

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(OPCBot.getOpcBotInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}

