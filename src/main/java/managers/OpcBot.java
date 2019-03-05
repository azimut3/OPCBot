package managers;

import data.Subscriprions.Subs;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class OpcBot extends TelegramLongPollingBot {
    private static OpcBot opcBotInstance;
    private static String botUsername, botToken;
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    public void onUpdateReceived(Update update) {
        String message = "";
        String chatId = "";
        if(update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
            chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        }
        if(update.hasMessage()) {
            message = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
        }
        CommandInterpreter.processCommand(message, createMsg(chatId));
        //reply(chatId, message);
    }


    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     */

    public synchronized SendMessage createMsg (String chatId) {
        SendMessage sendMessage = new SendMessage();
        //sendMessage.enableHtml(true);
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.HTML);

        return sendMessage;
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param sendMessage Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMsg(SendMessage sendMessage, String text) {
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
        }
    }

    public synchronized void sendFewMsg(SendMessage mes, String[] texts) {
        for (String text : texts){
            sendMsg(mes, text);
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Method creates an instance of managers.OpcBot
     * @return instance of managers.OpcBot
     */
    public static OpcBot getOpcBotInstance() {
        if (opcBotInstance == null) opcBotInstance = new OpcBot();
        return opcBotInstance;
    }

    public static void setBotUsername(String botUsername) {
        OpcBot.botUsername = botUsername;
    }

    public static void setBotToken(String botToken) {
        OpcBot.botToken = botToken;
    }


}

