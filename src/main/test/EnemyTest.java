package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

public class EnemyTest {
    
    @Test
    void enemyMoves() {
        Dungeon d = new Dungeon(8, 8);
        Switch s = new Switch(4, 1);
        Wall w1 = new Wall(2, 1);
        Wall w2 = new Wall(2, 3);
        Door door = new Door(2, 2, 1);
        Key k = new Key(4,2, 1);
        Enemy e = new Enemy(3, 1, d);
        Boulder b = new Boulder(2, 4);
        d.addEntity(s);
        d.addEntity(w1);
        d.addEntity(w2);
        d.addEntity(door);
        d.addEntity(k);
        d.addEntity(e);
        d.addEntity(b);
        ArrayList<String> movement = new ArrayList<>();
        ArrayList<String> movement1 = new ArrayList<>();
        movement.add("RIGHT");
        movement.add("DOWN");
        movement.add("LEFT");
        movement.add("LEFT");
        e.moveEnemy(movement);
        assertEquals(e.getX(), 3);
        assertEquals(e.getY(), 2);
        movement1.add("DOWN");
        movement1.add("LEFT");
        e.moveEnemy(movement1);
        assertEquals(e.getX(), 3);
        assertEquals(e.getY(), 3);
        movement1.add("DOWN");
        movement1.add("LEFT");
        e.moveEnemy(movement1);
        assertEquals(e.getX(), 3);
        assertEquals(e.getY(), 4);
        movement1.add("DOWN");
        movement1.add("LEFT");
        e.moveEnemy(movement1);
        assertEquals(e.getX(), 2);
        assertEquals(e.getY(), 5);
        movement1.add("DOWN");
        movement1.add("LEFT");
        movement1.add("UP");
        e.moveEnemy(movement1);
        assertEquals(e.getX(), 1);
        assertEquals(e.getY(), 5);
    }
}