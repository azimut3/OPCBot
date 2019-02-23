package managers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {

    public static void getMainKeyboard(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Порт"));
        keyboardFirstRow.add(new KeyboardButton("Погода"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("О боте"));
        keyboardSecondRow.add(new KeyboardButton("Поделиться"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    public static void setPortKeyboard(SendMessage sendMessage, boolean portMenu) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        if (portMenu) {
            buttons1.add(new InlineKeyboardButton().setText("Отслеживать обновления")
                    .setCallbackData("/subscribePort"));
            buttons.add(buttons1);
        } else {
            buttons1.add(new InlineKeyboardButton().setText("Отслеживать статус причалов")
                    .setCallbackData("/subBerthUpdate"));
            buttons2.add(new InlineKeyboardButton().setText("Отслеживать причалы")
                    .setCallbackData("/subBerth"));
            buttons.add(buttons1);
            buttons.add(buttons2);
        }
        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(markupKeyboard);
        markupKeyboard.setKeyboard(buttons);
    }

    public static void setWeatherKeyboard(SendMessage sendMessage, boolean showForecast) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        if (showForecast) {
            buttons1.add(new InlineKeyboardButton().setText("Прогноз на 3 дня")
                    .setCallbackData("/forecast"));
            buttons.add(buttons1);
        }
        buttons2.add(new InlineKeyboardButton().setText("Отслеживать погоду")
                .setCallbackData("/weatherSubscription"));
        buttons.add(buttons2);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(markupKeyboard);
        markupKeyboard.setKeyboard(buttons);
    }

    public static void setShareKeyboard(SendMessage sendMessage, String text) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(new InlineKeyboardButton().setText("Поделиться").setSwitchInlineQuery(text));
        buttons.add(buttons2);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(markupKeyboard);
        markupKeyboard.setKeyboard(buttons);
    }

    public static void setWeatherSubscribeKeyboard(SendMessage sendMessage) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons2 = new ArrayList<>();
        buttons2.add(new InlineKeyboardButton().setText("Подписаться").setCallbackData("/subscribeWeather"));;
        buttons.add(buttons2);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        sendMessage.setReplyMarkup(markupKeyboard);
        markupKeyboard.setKeyboard(buttons);
    }
}
