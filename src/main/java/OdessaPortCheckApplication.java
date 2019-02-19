import managers.PortUpdate;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


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

        Thread portUpdate = new PortUpdate("PortUpdater");
        portUpdate.start();
        OPCBot.setBotToken("711608450:AAGcmLVZLBskemrf88PFKJ5b_3j8ATQl7yg");
        OPCBot.setBotUsername("OdessaPortCheck");

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(OPCBot.getOpcBotInstance());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}

