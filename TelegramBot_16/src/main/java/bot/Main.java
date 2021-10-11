package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {

        String BOT_NAME = "smart_test_xatakon_bot";
        String BOT_TOKEN = "1958328861:AAHZL7MJmYObnE7WGBxWu7wsnZBLHiJGJD4";

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(BOT_NAME, BOT_TOKEN));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
