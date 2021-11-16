package bot;

import bot.Commands.PlayCommand;
import bot.Commands.StartCommand;
import bot.Game.MainGame;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class Bot extends TelegramLongPollingCommandBot {

    private final String botName;
    private final String botToken;

    DbHandler dbHandler = DbHandler.getInstance();
    MainGame mainGame = new MainGame();

    public Bot(String botName, String botToken) throws SQLException {
        this.botName = botName;
        this.botToken = botToken;

        register(new StartCommand("start", "Start"));
        register(new StartCommand("help", "Help"));
        register(new PlayCommand("play", "Play game", mainGame));
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {

        String msg = null;
        try {
            msg = mainGame.inGame(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!msg.equals("")) sendAnswer(update.getMessage().getChatId(), msg);
    }

    private void sendAnswer(Long chatId, String text){
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
