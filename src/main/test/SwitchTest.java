package test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class SwitchTest {
    @Test
    public void switchTest(){
        Dungeon d = new Dungeon(5, 6);
        assertEquals(d.getHeight(), 6);
        Switch s = new Switch(2, 2);
        d.addEntity(s);
        assertEquals(s.getX(), 2);
        assertEquals(s.getY(), 2);
        Player p = new Player(d, 2, 1);
        d.addEntity(p);
        p.moveDown();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
        p.moveDown();
        Boulder b = new Boulder(2, 2);
        d.addEntity(b);
        d.initialise();
        assertEquals(s.isStatus(), true);
    }

    @Test
    public void moveBoulders(){
        Dungeon d = new Dungeon(5, 6);
        Boulder b = new Boulder(2, 2);
        Boulder b2 = new Boulder(2, 3);
        Wall w = new Wall(3, 2);
        Switch s = new Switch(2, 2);
        d.addEntity(s);
        d.addEntity(b);
        d.addEntity(b2);
        d.addEntity(w);
        d.initialise();
        Player p = new Player(d, 2, 1);
        p.moveDown();
        p.moveLeft();
        p.moveDown();
        p.moveRight();
        assertEquals(b.getX(), 2);
        assertEquals(b.getY(), 2);
        assertEquals(b2.getX(), 2);
        assertEquals(b2.getY(), 3);
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 2);
        // Remove boulder from switch
        p.moveDown();
        p.moveRight();
        p.moveUp();
        p.moveRight();
        assertEquals(b.getX(), 2);
        assertEquals(b.getY(), 1);
        p.moveDown();
        p.moveDown();
        p.moveRight();
        p.moveRight();
        p.moveUp();
        p.moveLeft();
        p.moveDown();
        p.moveLeft();
        p.moveUp();
        p.moveUp();
        assertEquals(b2.getX(), 2);
        assertEquals(b2.getY(), 2);
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 3);
        p.moveDown();
        Boulder b3 = new Boulder(2, 3);
        d.addEntity(b3);
        p.moveUp();
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 4);
    }
}