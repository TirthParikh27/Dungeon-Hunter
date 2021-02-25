package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import unsw.dungeon.*;

public class TestDoorKey {
    
    public Entity getEnemy(List<Entity> list) {
        for (Entity entity : list) {
            if (entity instanceof Enemy) {
                return entity;
            }
        }
        return null;
    }
    @Test
    void TestDoor(){
        
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("x", 2);
        jsonPlayer.put("y", 1);
        jsonPlayer.put("type", "player");

        JSONObject jsonTreasure = new JSONObject();
        jsonTreasure.put("x", 3);
        jsonTreasure.put("y", 1);
        jsonTreasure.put("id", 1);
        jsonTreasure.put("type", "key");

        JSONObject jsonExit = new JSONObject();
        jsonExit.put("x", 1);
        jsonExit.put("y", 1);
        jsonExit.put("id", 1);
        jsonExit.put("type", "door");
           
        JSONObject jsonEnemy = new JSONObject();
        jsonEnemy.put("x", 4);
        jsonEnemy.put("y", 1);
        jsonEnemy.put("type", "enemy");

        /*JSONObject jsonSword = new JSONObject();
        jsonSword.put("x", 0);
        jsonSword.put("y", 1);
        jsonSword.put("type", "sword");*/

        JSONArray array = new JSONArray();
        array.put(jsonPlayer);
        array.put(jsonTreasure);
        array.put(jsonExit);
        array.put(jsonEnemy);
        //array.put(jsonSword);

        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "enemies");
        
        JSONObject finalJson = new JSONObject();
        finalJson.put("width" , 5);
        finalJson.put("height" , 5);
        finalJson.put("entities", array);
        finalJson.put("goal-condition", exitGoal);

        DungeonLoader loader = new DungeonMockLoader(finalJson);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();

        player.moveLeft();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);

        player.moveRight();
        player.moveLeft();
        player.moveLeft();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);

        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);

        player.moveLeft();
        assertEquals(player.getX(), 0);
        assertEquals(player.getY(), 1);

        

        Enemy ene = (Enemy)getEnemy(dungeon.getEntities());

        List<String> moveList = new ArrayList<String>();
        moveList.add("LEFT");
        moveList.add("LEFT");
        moveList.add("LEFT");
        moveList.add("LEFT");
        moveList.add("LEFT");
        ene.moveEnemy((ArrayList<String>) moveList);

        assertTrue(ene.getX() == 0);
        assertTrue(ene.getY() == 1);
        

        
        
    }

    @Test
    void TestWrongKey(){
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("x", 2);
        jsonPlayer.put("y", 1);
        jsonPlayer.put("type", "player");

        JSONObject jsonTreasure = new JSONObject();
        jsonTreasure.put("x", 3);
        jsonTreasure.put("y", 1);
        jsonTreasure.put("id", 2);
        jsonTreasure.put("type", "key");

        JSONObject jsonExit = new JSONObject();
        jsonExit.put("x", 1);
        jsonExit.put("y", 1);
        jsonExit.put("id", 1);
        jsonExit.put("type", "door");

        JSONArray array = new JSONArray();
        array.put(jsonPlayer);
        array.put(jsonTreasure);
        array.put(jsonExit);

        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "exit");
        
        JSONObject finalJson = new JSONObject();
        finalJson.put("width" , 5);
        finalJson.put("height" , 5);
        finalJson.put("entities", array);
        finalJson.put("goal-condition", exitGoal);

        DungeonLoader loader = new DungeonMockLoader(finalJson);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();

        player.moveLeft();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);

        player.moveRight();
        player.moveLeft();
        player.moveLeft();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 1);
        
    }


    
}