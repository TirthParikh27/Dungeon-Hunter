package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;
public class BoulderTest {
    
    @Test
    public void boulderTest(){
        Dungeon d = new Dungeon(5, 6);
        assertEquals(d.getWidth(), 5);
        Boulder boulder = new Boulder(2, 2);
        assertEquals(boulder.getX(), 2);
        assertEquals(boulder.getY(), 2);
    }

    @Test
    public void boulderMovement(){
        Dungeon d = new Dungeon(5, 6);
        assertEquals(d.getWidth(), 5);
        Boulder boulder = new Boulder(2, 2);
        assertEquals(boulder.getX(), 2);
        assertEquals(boulder.getY(), 2);
        d.addEntity(boulder);
        Player p = new Player(d, 2, 1);
        d.setPlayer(p);
        p.moveDown();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        assertEquals(boulder.getX(), 2);
        assertEquals(boulder.getY(), 3);
        // Add wall 
        Wall w = new Wall(2, 4);
        d.addEntity(w);
        p.moveDown();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        assertEquals(boulder.getX(), 2);
        assertEquals(boulder.getY(), 3);
        p.moveLeft();
        p.moveDown();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);
        // Doesnt move with boulder ahead 
        Boulder b2 = new Boulder(3, 3);
        d.addEntity(b2);
        p.moveRight();
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 3);
    }

}