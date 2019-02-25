package managers;

import data.DatabaseConnector.JdbcConnector;
import data.Subscriprions.SubsLauncher;
import data.Weather.CurrentWeatherThread;
import data.Weather.ForecastThread;
import data.Port.PortUpdateThread;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


public class OdessaPortCheckApplication {

    public static void main(String[] args) {
        System.out.println("managers.OpcBot: starting...");
       /* Properties botInfo = new Properties();
        try {
            FileInputStream fis = new FileInputStream(managers.OdessaPortCheckApplication.class
                    .getResource("bot.properties").getFile());
            botInfo.load(fis);

            managers.OpcBot.setBotToken(botInfo.getProperty("botToken"));
            managers.OpcBot.setBotUsername(botInfo.getProperty("botName"));

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        JdbcConnector jdbcConnector = new JdbcConnector();
        Thread portUpdateThread = new PortUpdateThread("portUpdateThread");
        portUpdateThread.start();
        Thread currentWeatherThread = new CurrentWeatherThread("currentWeatherThread");
        currentWeatherThread.start();
        Thread forecastWeatherThread = new ForecastThread("forecastWeatherThread");
        forecastWeatherThread.start();
        SubsLauncher.LaunchSubs();
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

