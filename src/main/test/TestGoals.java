package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import unsw.dungeon.*;

public class TestGoals {
    @Test
    void ExitGoal(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon,1 , 1);
        ExitGate exit = new ExitGate(2, 1);
        dungeon.setPlayer(player);
        dungeon.addEntity(exit);

        ExitGoal exitGoal = new ExitGoal("exit", dungeon);
        dungeon.setGoal(exitGoal);

        assertFalse(dungeon.getGoal().check());


        player.moveRight();
        assertTrue(dungeon.getGoal().check());
    }

    @Test
    void TreasureGoal(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon,1 , 1);
        Treasure treasure = new Treasure(2, 1);
        dungeon.setPlayer(player);
        dungeon.addEntity(treasure);
        dungeon.initialise();

        TreasureGoal treasureGoal = new TreasureGoal("exit", dungeon);
        dungeon.setGoal(treasureGoal);
        assertFalse(dungeon.getGoal().check());


        player.moveRight();
        assertTrue(dungeon.getGoal().check());
        
    }

    @Test
    void SwitchGoal(){
        Dungeon dungeon = new Dungeon(4, 4);
        Player player = new Player(dungeon,1 , 1);
        Switch switch1 = new Switch(3, 1);
        Boulder boulder = new Boulder(2, 1);
        dungeon.setPlayer(player);
        dungeon.addEntity(switch1);
        dungeon.addEntity(boulder);

        SwitchGoal goal = new SwitchGoal("switc", dungeon);
        dungeon.setGoal(goal);
        dungeon.initialise();
        
        assertFalse(dungeon.getGoal().check());

        player.moveRight();
        assertTrue(dungeon.getGoal().check());
    }

    @Test
    void EnemyGoal(){
        Dungeon dungeon = new Dungeon(5, 5);
        Player player = new Player(dungeon,1 , 1);
        Enemy enemy = new Enemy(4, 1, dungeon);
        Sword sword = new Sword(2, 1);
        dungeon.setPlayer(player);
        dungeon.addEntity(sword);
        dungeon.addEntity(enemy);
        EnemyGoal goal = new EnemyGoal("enemy", dungeon);
        dungeon.setGoal(goal);
        dungeon.initialise();

        assertFalse(dungeon.getGoal().check());

        player.moveRight();
        player.moveRight();
        player.moveRight();

        assertTrue(dungeon.getGoal().check());

    }

    @Test
    void TestAndGoal(){
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("x", 1);
        jsonPlayer.put("y", 1);
        jsonPlayer.put("type", "player");

        JSONObject jsonTreasure = new JSONObject();
        jsonTreasure.put("x", 2);
        jsonTreasure.put("y", 1);
        jsonTreasure.put("type", "treasure");

        JSONObject jsonExit = new JSONObject();
        jsonExit.put("x", 3);
        jsonExit.put("y", 1);
        jsonExit.put("type", "exit");

        JSONArray array = new JSONArray();
        array.put(jsonPlayer);
        array.put(jsonTreasure);
        array.put(jsonExit);

        JSONObject jsonGoal= new JSONObject();
        jsonGoal.put("goal", "AND");
        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "exit");
        JSONObject treasureGoal = new JSONObject();
        treasureGoal.put("goal", "treasure");
        JSONArray goalArray = new JSONArray();
        goalArray.put(exitGoal);
        goalArray.put(treasureGoal);
        jsonGoal.put("subgoals", goalArray);

        JSONObject finalJson = new JSONObject();
        finalJson.put("width" , 5);
        finalJson.put("height" , 5);
        finalJson.put("entities", array);
        finalJson.put("goal-condition", jsonGoal);

        DungeonLoader loader = new DungeonMockLoader(finalJson);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();

        
        assertFalse(dungeon.getGoal().check());

        player.moveRight();

        assertFalse(dungeon.getGoal().check());

        player.moveRight();


        assertTrue(dungeon.getGoal().check());

    }

    @Test
    void TestOrGoal(){
        JSONObject jsonPlayer = new JSONObject();
        jsonPlayer.put("x", 1);
        jsonPlayer.put("y", 1);
        jsonPlayer.put("type", "player");

        JSONObject jsonTreasure = new JSONObject();
        jsonTreasure.put("x", 2);
        jsonTreasure.put("y", 1);
        jsonTreasure.put("type", "treasure");

        JSONObject jsonExit = new JSONObject();
        jsonExit.put("x", 3);
        jsonExit.put("y", 1);
        jsonExit.put("type", "exit");

        JSONArray array = new JSONArray();
        array.put(jsonPlayer);
        array.put(jsonTreasure);
        array.put(jsonExit);

        JSONObject jsonGoal= new JSONObject();
        jsonGoal.put("goal", "OR");
        JSONObject exitGoal = new JSONObject();
        exitGoal.put("goal", "exit");
        JSONObject treasureGoal = new JSONObject();
        treasureGoal.put("goal", "treasure");
        JSONArray goalArray = new JSONArray();
        goalArray.put(exitGoal);
        goalArray.put(treasureGoal);
        jsonGoal.put("subgoals", goalArray);

        JSONObject finalJson = new JSONObject();
        finalJson.put("width" , 5);
        finalJson.put("height" , 5);
        finalJson.put("entities", array);
        finalJson.put("goal-condition", jsonGoal);

        DungeonLoader loader = new DungeonMockLoader(finalJson);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();

        
        assertFalse(dungeon.getGoal().check());

        player.moveRight();

        assertTrue(dungeon.getGoal().check());

        player.moveRight();


        assertTrue(dungeon.getGoal().check());
    }
}