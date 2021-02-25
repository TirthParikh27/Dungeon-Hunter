package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import unsw.dungeon.*;

public class TestSword {
    @Test
    void TestSword5(){
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("x", 1);
        jsonPlayer.put("y", 1);
        jsonPlayer.put("type", "player");

        JSONObject jsonPlayer2 = new JSONObject();
        jsonPlayer2.put("x", 8);
        jsonPlayer2.put("y", 1);
        jsonPlayer2.put("type", "enemy");

        JSONObject jsonPlayer3 = new JSONObject();
        jsonPlayer3.put("x", 9);
        jsonPlayer3.put("y", 1);
        jsonPlayer3.put("type", "enemy");

        JSONObject jsonPlayer4 = new JSONObject();
        jsonPlayer4.put("x", 10);
        jsonPlayer4.put("y", 1);
        jsonPlayer4.put("type", "enemy");
        
        JSONObject jsonPlayer5 = new JSONObject();
        jsonPlayer5.put("x", 7);
        jsonPlayer5.put("y", 1);
        jsonPlayer5.put("type", "enemy");
        
        JSONObject jsonPlayer6 = new JSONObject();
        jsonPlayer6.put("x", 6);
        jsonPlayer6.put("y", 1);
        jsonPlayer6.put("type", "enemy");

        JSONObject jsonPlayer7 = new JSONObject();
        jsonPlayer7.put("x", 2);
        jsonPlayer7.put("y", 1);
        jsonPlayer7.put("id" , 1);
        jsonPlayer7.put("type", "sword");

        JSONArray array = new JSONArray();
        array.put(jsonPlayer);
        array.put(jsonPlayer2);
        array.put(jsonPlayer3);
        array.put(jsonPlayer4);
        array.put(jsonPlayer5);
        array.put(jsonPlayer6);
        array.put(jsonPlayer7);

        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "enemies");
        
        JSONObject finalJson = new JSONObject();
        finalJson.put("width" , 10);
        finalJson.put("height" , 10);
        finalJson.put("entities", array);
        finalJson.put("goal-condition", exitGoal);

        DungeonLoader loader = new DungeonMockLoader(finalJson);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();

        player.moveRight();

        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        assertTrue(dungeon.getGoal().check());
    }

    @Test
    void TestSword6(){
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("x", 1);
        jsonPlayer.put("y", 1);
        jsonPlayer.put("type", "player");

        JSONObject jsonPlayer2 = new JSONObject();
        jsonPlayer2.put("x", 8);
        jsonPlayer2.put("y", 1);
        jsonPlayer2.put("type", "enemy");

        JSONObject jsonPlayer3 = new JSONObject();
        jsonPlayer3.put("x", 9);
        jsonPlayer3.put("y", 1);
        jsonPlayer3.put("type", "enemy");

        JSONObject jsonPlayer4 = new JSONObject();
        jsonPlayer4.put("x", 10);
        jsonPlayer4.put("y", 1);
        jsonPlayer4.put("type", "enemy");
        
        JSONObject jsonPlayer5 = new JSONObject();
        jsonPlayer5.put("x", 7);
        jsonPlayer5.put("y", 1);
        jsonPlayer5.put("type", "enemy");
        
        JSONObject jsonPlayer6 = new JSONObject();
        jsonPlayer6.put("x", 6);
        jsonPlayer6.put("y", 1);
        jsonPlayer6.put("type", "enemy");

        JSONObject jsonPlayer7 = new JSONObject();
        jsonPlayer7.put("x", 2);
        jsonPlayer7.put("y", 1);
        jsonPlayer7.put("id", 1);
        jsonPlayer7.put("type", "sword");

        JSONObject jsonPlayer8 = new JSONObject();
        jsonPlayer8.put("x", 5);
        jsonPlayer8.put("y", 1);
        jsonPlayer8.put("type", "enemy");

        JSONArray array = new JSONArray();
        array.put(jsonPlayer);
        array.put(jsonPlayer2);
        array.put(jsonPlayer3);
        array.put(jsonPlayer4);
        array.put(jsonPlayer5);
        array.put(jsonPlayer6);
        array.put(jsonPlayer7);
        array.put(jsonPlayer8);

        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "enemies");
        
        JSONObject finalJson = new JSONObject();
        finalJson.put("width" , 10);
        finalJson.put("height" , 10);
        finalJson.put("entities", array);
        finalJson.put("goal-condition", exitGoal);

        DungeonLoader loader = new DungeonMockLoader(finalJson);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();

        player.moveRight();

        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

        assertFalse(dungeon.getGoal().check());
    }

    
}