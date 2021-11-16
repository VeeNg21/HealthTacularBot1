package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties properties = new Properties();

        try {
            InputStream is = Main.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(is);

            String botName = properties.getProperty("bot_name");
            String botToken = properties.getProperty("bot_token");

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(botName, botToken));

            is.close();
        } catch (TelegramApiException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
