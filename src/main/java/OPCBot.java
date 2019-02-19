import managers.CommandInterpreter;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class OPCBot extends TelegramLongPollingBot {
    private static OPCBot opcBotInstance;
    private static String botUsername, botToken;
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        //sendMsg(update.getMessage().getChatId().toString(), message + "reply");
        sendMsg(chatId, CommandInterpreter.processCommand(message));
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        //sendMessage.enableHtml(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        sendMessage.setParseMode(ParseMode.HTML);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
            //log.log(Level.SEVERE, "Exception: ", e.toString());
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
     * Method creates an instance of OPCBot
     * @return instance of OPCBot
     */
    public static OPCBot getOpcBotInstance() {
        if (opcBotInstance == null) opcBotInstance = new OPCBot();
        return opcBotInstance;
    }

    public static void setBotUsername(String botUsername) {
        OPCBot.botUsername = botUsername;
    }

    public static void setBotToken(String botToken) {
        OPCBot.botToken = botToken;
    }
}

