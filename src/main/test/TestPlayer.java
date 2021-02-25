package test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import unsw.dungeon.*;

public class TestPlayer {
    @Test
    void testMove(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon,1 , 1);
        dungeon.setPlayer(player);
        Player temp = dungeon.getPlayer();
        assertTrue(temp.getX() == player.getX());
        assertTrue(temp.getY() == player.getY());

        player.moveUp();
        assertTrue(player.getX() == 1);
        assertTrue(player.getY() == 0);

        player.moveDown();
        assertTrue(player.getX() == 1);
        assertTrue(player.getY() == 1);

        player.moveRight();
        assertTrue(player.getX() == 2);
        assertTrue(player.getY() == 1);

        player.moveLeft();
        assertTrue(player.getX() == 1);
        assertTrue(player.getY() == 1);

    }

    @Test
    void testWall(){
        Dungeon dungeon = new Dungeon(3, 3);
        Player player = new Player(dungeon,1 , 1);
        Wall wall = new Wall(2, 1);
        dungeon.setPlayer(player); 
        dungeon.addEntity(wall);
        
        player.moveRight();
        assertTrue(player.getX() == 1);
        assertTrue(player.getY() == 1);

    }
    
    
}