package bot.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainGame extends DefaultPlayer {

    private final HashMap<String, String> items = new HashMap<>();
    public HashMap<Long, DefaultPlayer> players = new HashMap<>();

    public MainGame() {

        items.put("carrots", "eye");
        items.put("ginger", "stomach");
        items.put("tomatoes", "heart");
        items.put("olives", "ovaries");
        items.put("onions", "cells");
        items.put("beans", "kidneys");
        items.put("mushroom", "ears");
        items.put("broccoli", "cells");
        items.put("celery", "bones");
    }

    public void getRandomValue(DefaultPlayer defaultPlayer){
        Random random = new Random();
        List<String> keys = new ArrayList<>(items.keySet());
        String question = keys.get(random.nextInt(keys.size()));
        defaultPlayer.setCurrentAnswer(items.get(question));
        defaultPlayer.setInGame(true);
        defaultPlayer.setExpectedAnswer("Which organ does this vegetable help: " + question);
    }

    public String play(String message, DefaultPlayer defaultPlayer){
        String result = "Incorrect benefit";
        if(!message.isEmpty()) {
            if(message.equals(defaultPlayer.getCurrentAnswer())){
                defaultPlayer.upCount();
                getRandomValue(defaultPlayer);
                result = "Correct benefit\nTo end the game write: stop.\n" + defaultPlayer;
            }
        }
        return result;
    }

    public DefaultPlayer getPlayer(Long chatId) {
        return players.get(chatId);
    }

    public void removePlayer(Long chatId) {
        players.remove(chatId);
    }

    public void setPlayerList(Long chatId, DefaultPlayer defaultPlayer) {
        players.put(chatId, defaultPlayer);
    }

}
