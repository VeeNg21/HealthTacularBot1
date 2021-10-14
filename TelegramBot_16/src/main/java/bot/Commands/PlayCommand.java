package bot.Commands;

import bot.Bot;
import bot.Game.MainGame;
import bot.Game.DefaultPlayer;
import bot.Main;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class PlayCommand extends ServiceCommand {

    private MainGame mainGame;

    public PlayCommand(String identifier, String description, MainGame mainGame){
        super(identifier, description);
        this.mainGame = mainGame;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());


        DefaultPlayer defaultPlayer = new DefaultPlayer();
        defaultPlayer.setChatId(chat.getId());

        mainGame.setPlayerList(chat.getId(), defaultPlayer);

        mainGame.getRandomValue(mainGame.getPlayer(defaultPlayer.getChatId()));

        String result = "To end the game write: stop.\n" + defaultPlayer;
        sendAnswer(absSender, defaultPlayer.getChatId(), this.getCommandIdentifier(), userName, result);

    }
}
