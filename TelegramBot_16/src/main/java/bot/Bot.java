package bot;

import bot.Commands.HelpCommand;
import bot.Commands.PlayCommand;
import bot.Commands.StartCommand;
import bot.Game.MainGame;
import bot.Game.Player;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class Bot extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public static boolean inGame = false;

    public static HashMap<Long, Player> players = new HashMap<>();

    // Изменение статической переменной говорит о начале игры и все следующие сообщения, кроме stop будут относиться к игре.


    public Bot(String BOT_NAME, String BOT_TOKEN){
        super();
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        register(new StartCommand("start", "Start"));
        register(new PlayCommand("play", "Play game"));
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


    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {

        if(inGame){
            if(update.hasMessage() && update.getMessage().hasText()){
                if(update.getMessage().getText().equals("stop")){
                    inGame = false;
                    String message = "End game";
                    players.remove(update.getMessage().getChatId());
                    setAnswer(update.getMessage().getChatId(), message);
                } else {
                    String msg = update.getMessage().getText();
                    String result = MainGame.play(msg, players.get(update.getMessage().getChatId()));
                    setAnswer(update.getMessage().getChatId(), result);
                }
            }
        }

    }

    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    private String getUserName(Message msg){
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName: String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String text){
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
