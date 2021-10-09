package bot.Commands;

import bot.Bot;
import bot.Game.MainGame;
import bot.Game.Player;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class PlayCommand extends ServiceCommand {

    public PlayCommand(String identifier, String description){
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        Player player = new Player();
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        MainGame.getRandomValue(player);
        String result = "To end the game write: stop.\n" + player;
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, result);

        Bot.inGame = true;
        Bot.players.put(chat.getId(), player);

    }
}
