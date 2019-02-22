import data.Weather.CurrentWeatherThread;
import data.Weather.ForecastThread;
import data.Port.PortUpdate;
import managers.SecretData;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


public class OdessaPortCheckApplication {

    public static void main(String[] args) {
        System.out.println("OpcBot: starting...");
       /* Properties botInfo = new Properties();
        try {
            FileInputStream fis = new FileInputStream(OdessaPortCheckApplication.class
                    .getResource("bot.properties").getFile());
            botInfo.load(fis);

            OpcBot.setBotToken(botInfo.getProperty("botToken"));
            OpcBot.setBotUsername(botInfo.getProperty("botName"));

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
        OpcBot.setBotToken(SecretData.botToken);
        OpcBot.setBotUsername(SecretData.botName);

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(OpcBot.getOpcBotInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}

