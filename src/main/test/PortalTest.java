package test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.dungeon.*;

public class PortalTest {
    
    @Test
    void playerTeleport() {
        JSONObject playerJSON = new JSONObject().put("x", 1).put("y", 1).put("type", "player");

        JSONObject p1 = new JSONObject().put("x", 2).put("y", 2).put("type", "portal").put("id", 1);

        JSONObject p2 = new JSONObject().put("x", 7).put("y", 7).put("type", "portal").put("id", 1);

        JSONArray entitiesJSON = new JSONArray().put(playerJSON).put(p1).put(p2);

        JSONObject goalJSON = new JSONObject().put("goal", "enemies");

        JSONObject json = new JSONObject().put("width", 10).put("height", 10).put("entities", entitiesJSON)
                .put("goal-condition", goalJSON);

        DungeonLoader loader = new DungeonMockLoader(json);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();
        player.moveDown();
        player.moveRight();
        assertEquals(player.getX(), 7);
        assertEquals(player.getY(), 7);
        player.moveLeft();
        player.moveRight();
        assertEquals(player.getX(), 2);
        assertEquals(player.getY(), 2);
    }

    @Test
    void boulderTeleport(){
        JSONObject playerJSON = new JSONObject().put("x", 1).put("y", 3).put("type", "player");

        JSONObject p1 = new JSONObject().put("x", 3).put("y", 3).put("type", "portal").put("id", 1);

        JSONObject p2 = new JSONObject().put("x", 5).put("y", 5).put("type", "portal").put("id", 1);

        JSONObject b1 =  new JSONObject().put("x", 2).put("y", 3).put("type", "boulder");
        JSONObject b2 =  new JSONObject().put("x", 4).put("y", 5).put("type", "boulder");

        JSONArray entitiesJSON = new JSONArray().put(playerJSON).put(p1).put(p2).put(b1).put(b2);

        JSONObject goalJSON = new JSONObject().put("goal", "enemies");

        JSONObject json = new JSONObject().put("width", 10).put("height", 10).put("entities", entitiesJSON)
                .put("goal-condition", goalJSON);

        DungeonLoader loader = new DungeonMockLoader(json);
        Dungeon dungeon = loader.load();
        Player player = dungeon.getPlayer();
        List<Entity> list = dungeon.getEntities();
        Boulder m1 = (Boulder)getBoulder(list, 0);
        Boulder m2 = (Boulder)getBoulder(list, 1);
        assertEquals(m2.getX(), 4);
        assertEquals(m2.getY(), 5);
        
        player.moveRight();
        
        assertEquals(m1.getX(), 5);
        assertEquals(m1.getY(), 5);
        player.moveUp();
        player.moveRight();
        player.moveRight();
        player.moveDown();
        player.moveLeft();
        assertEquals(player.getX(), 4);
        assertEquals(player.getY(), 3);
        player.moveDown();
        player.moveLeft();
        player.moveDown();
        player.moveRight();
       
        assertEquals(player.getX(), 3);
        assertEquals(player.getY(), 5);
        player.moveDown();
        player.moveRight();
        player.moveRight();
        player.moveRight();
        player.moveUp();
        player.moveLeft();
        assertEquals(player.getX(), 6);
        assertEquals(player.getY(), 5);
        player.moveUp();
        player.moveLeft();
        player.moveLeft();
        player.moveDown();
        assertEquals(player.getX(), 4);
        assertEquals(player.getY(), 5);
        player.moveRight();
        assertEquals(player.getX(), 3);
        assertEquals(player.getY(), 3);
        assertEquals(m1.getX(), 6);
        assertEquals(m1.getY(), 5);
        
    }

    public Entity getBoulder(List<Entity> list, int x) {
        for (Entity entity : list) {
            if (entity instanceof Boulder) {
                if (x == 0)
                    return entity;
                x--;
            }
        }
        return null;
    }
}