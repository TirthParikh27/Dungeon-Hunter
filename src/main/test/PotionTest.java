package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.dungeon.*;

public class PotionTest {

    @Test
    void potiontest() {
        JSONObject playerJSON = new JSONObject().put("x", 1).put("y", 1).put("type", "player");

        JSONObject potionJSON = new JSONObject().put("x", 2).put("y", 1).put("type", "invincibility");

        JSONObject enemy = new JSONObject().put("x", 6).put("y", 6).put("type", "enemy");

        JSONArray entitiesJSON = new JSONArray().put(playerJSON).put(potionJSON).put(enemy);

        JSONObject goalJSON = new JSONObject().put("goal", "enemies");

        JSONObject json = new JSONObject().put("width", 10).put("height", 10).put("entities", entitiesJSON)
                .put("goal-condition", goalJSON);

        DungeonLoader loader = new DungeonMockLoader(json);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();
        assertTrue(player.getState().equals("NORMAL"));
        Enemy e = (Enemy) getEnemy(dungeon.getEntities());
        assertTrue(e.getState() instanceof Brave);
        player.moveRight();
        assertTrue(player.getState().equals("INVINCIBLE"));
        assertTrue(e.getState() instanceof Coward);
        try
        {
            Thread.sleep(5500);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        assertTrue(e.getState() instanceof Brave);
        assertTrue(player.getState().equals("NORMAL"));
    }

    public Entity getEnemy(List<Entity> list) {
        for (Entity entity : list) {
            if (entity instanceof Enemy) {
                return entity;
            }
        }
        return null;
    }

    @Test
    void potionkills() {
        JSONObject playerJSON = new JSONObject().put("x", 1).put("y", 1).put("type", "player");

        JSONObject potionJSON = new JSONObject().put("x", 2).put("y", 2).put("type", "invincibility");

        JSONObject enemy = new JSONObject().put("x", 9).put("y", 1).put("type", "enemy");
        JSONObject boulder = new JSONObject().put("x", 8).put("y", 1).put("type", "boulder");
        JSONObject switch1 = new JSONObject().put("x", 7).put("y", 2).put("type", "switch");
        JSONObject switch2 = new JSONObject().put("x", 3).put("y", 2).put("type", "switch");

        JSONArray entitiesJSON = new JSONArray().put(playerJSON).put(potionJSON).put(enemy).put(boulder).put(switch1).put(switch2);

        JSONObject goalJSON = new JSONObject().put("goal", "enemies");

        JSONObject json = new JSONObject().put("width", 10).put("height", 10).put("entities", entitiesJSON)
                .put("goal-condition", goalJSON);

        DungeonLoader loader = new DungeonMockLoader(json);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();
        assertTrue(player.getState().equals("NORMAL"));
        Enemy e = (Enemy) getEnemy(dungeon.getEntities());
        assertTrue(e.getState() instanceof Brave);
        try
        {
            Thread.sleep(4000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        player.moveDown();
        try
        {
            Thread.sleep(15000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        
        player.moveRight();
        player.moveRight();
        player.moveRight();
        player.moveRight();
        player.moveRight();
        assertTrue(dungeon.getGoal().check());
    }
}