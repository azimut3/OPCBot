package managers;

import data.DatabaseConnector.StatsTable;
import data.DatabaseConnector.UserTableConnector;
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

        UserTableConnector userTableConnector = new UserTableConnector();
        Thread portUpdateThread = new PortUpdateThread("portUpdateThread");
        portUpdateThread.start();
        Thread currentWeatherThread = new CurrentWeatherThread("currentWeatherThread");
        currentWeatherThread.start();
        Thread forecastWeatherThread = new ForecastThread("forecastWeatherThread");
        forecastWeatherThread.start();
        SubsLauncher.LaunchSubs();
        StatsReaper reaper = new StatsReaper("statsReaper");
        reaper.start();
        OpcBot.setBotToken(SecretData.botToken);
        OpcBot.setBotUsername(SecretData.botName);
        StatsTable.recordStats();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(OpcBot.getOpcBotInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}

