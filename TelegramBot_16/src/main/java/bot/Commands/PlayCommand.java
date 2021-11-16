package bot.Commands;

import bot.DbHandler;
import bot.Game.MainGame;
import bot.Game.DefaultPlayer;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.sql.SQLException;

public class PlayCommand extends ServiceCommand {

    private final MainGame mainGame;
    private final DbHandler dbHandler;

    public PlayCommand(String identifier, String description, MainGame mainGame) throws SQLException {
        super(identifier, description);
        this.mainGame = mainGame;
        this.dbHandler = DbHandler.getInstance();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        DefaultPlayer defaultPlayer = new DefaultPlayer();
        defaultPlayer.setChatId(chat.getId());
        if(dbHandler.isInGame(chat.getId())) {

            String result = "The game is already running.\n";
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, result);

        } else {
        if(dbHandler.checkPlayer(chat.getId())) dbHandler.addPlayer(chat.getId());
            try {
                dbHandler.changePlay(chat.getId(), true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mainGame.setPlayerList(chat.getId(), defaultPlayer);

            try {
                mainGame.getRandomValue(mainGame.getPlayer(chat.getId()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String result = "To end the game write: stop.\n" + defaultPlayer;
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, result);
        }

    }
}
