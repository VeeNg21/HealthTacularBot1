package bot;

import bot.Commands.PlayCommand;
import bot.Commands.StartCommand;
import bot.Game.MainGame;
import bot.Game.DefaultPlayer;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingCommandBot {

    private final String botName;
    private final String botToken;

    MainGame mainGame = new MainGame();

    public Bot(String botName, String botToken){
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

    @Override
    public void processNonCommandUpdate(Update update) {

        String msg = mainGame.inGame(update);
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
