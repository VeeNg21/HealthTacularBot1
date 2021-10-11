package bot;

import bot.Commands.HelpCommand;
import bot.Commands.PlayCommand;
import bot.Commands.StartCommand;
import bot.Game.MainGame;
import bot.Game.DefaultPlayer;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class Bot extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    MainGame mainGame = new MainGame();

    public Bot(String BOT_NAME, String BOT_TOKEN){
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        register(new StartCommand("start", "Start"));
        register(new PlayCommand("play", "Play game", mainGame));
        register(new HelpCommand("help", "Help"));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }



    @Override
    public void processNonCommandUpdate(Update update) {

        DefaultPlayer defaultPlayer = mainGame.getPlayer(update.getMessage().getChatId());

        if(defaultPlayer.isInGame()){
            if(update.hasMessage() && update.getMessage().hasText()){
                if(update.getMessage().getText().equals("stop")){
                    defaultPlayer.setInGame(false);
                    String message = "End game";
                    mainGame.removePlayer(update.getMessage().getChatId());
                    sendAnswer(update.getMessage().getChatId(), message);
                } else {
                    String msg = update.getMessage().getText();
                    String result = mainGame.play(msg, defaultPlayer);
                    sendAnswer(update.getMessage().getChatId(), result);
                }
            }
        }

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
