package bot.Commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends ServiceCommand {


    private final String answer = "Hello! \n" +
            "I am a bot that tells you which particular body part a vegetable is beneficial toYou get a random vegetable and input an organ which it helps \n" +
            "To start the game type /play \n" +
            "To display /help, type help\n";

    public StartCommand(String identifier, String description){
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, answer);
    }
}
