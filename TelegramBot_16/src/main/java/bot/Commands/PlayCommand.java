package bot.Commands;

import bot.Game.DefaultPlayer;
import bot.Game.MainGame;
import bot.models.DefaultPlayerDAO;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class PlayCommand extends ServiceCommand {

    private MainGame mainGame;
//    private DbHandler dbHandler;
    private DefaultPlayerDAO defaultPlayerDAO;

    public PlayCommand(String identifier, String description, MainGame mainGame, DefaultPlayerDAO defaultPlayerDAO){
        super(identifier, description);
        this.mainGame = mainGame;
        this.defaultPlayerDAO = defaultPlayerDAO;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());


        DefaultPlayer defaultPlayer;

        if (defaultPlayerDAO.findByChatID(chat.getId()) == null) {
            defaultPlayer = new DefaultPlayer(chat.getId(), true);
            defaultPlayerDAO.save(defaultPlayer);
        } else  {
            defaultPlayer = defaultPlayerDAO.findByChatID(chat.getId());
            defaultPlayer.setInGame(true);
            defaultPlayerDAO.update(defaultPlayer);
        }

        mainGame.getRandomValue(defaultPlayer);

        String result = "To end the game write: stop.\n" + defaultPlayer;
        sendAnswer(absSender, defaultPlayer.getChatId(), this.getCommandIdentifier(), userName, result);

    }
}
