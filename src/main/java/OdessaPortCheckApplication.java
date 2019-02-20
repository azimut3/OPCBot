import data.Weather.CurrentWeatherThread;
import data.Weather.ForecastThread;
import managers.PortUpdate;
import managers.SecretData;
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
        Thread currentWeatherThread = new CurrentWeatherThread("currentWeatherThread");
        currentWeatherThread.start();
        Thread forecastWeatherThread = new ForecastThread("forecastWeatherThread");
        forecastWeatherThread.start();
        OPCBot.setBotToken(SecretData.botToken);
        OPCBot.setBotUsername(SecretData.botName);

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(OPCBot.getOpcBotInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}

