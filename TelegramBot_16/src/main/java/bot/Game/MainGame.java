package bot.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainGame extends Player {

    public static HashMap<String, String> items = new HashMap<>();
    static {
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

    public static void getRandomValue(Player player){
        Random random = new Random();
        List<String> keys = new ArrayList<>(items.keySet());
        String question = keys.get(random.nextInt(keys.size()));
        player.setCurrentAnswer(items.get(question));

        player.setExpectedAnswer("Which organ does this vegetable help: " + question);
    }

    public static String play(String message, Player player){
        String result = "Incorrect benefit";
        if(!message.isEmpty()) {
            if(message.equals(player.getCurrentAnswer())){
                player.upCount();
                getRandomValue(player);
                result = "Correct benefit\nTo end the game write: stop.\n" + player;
            }
        }
        return result;
    }
}
