package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.input.KeyCode;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements Subject {

    private Dungeon dungeon;
    private CopyOnWriteArrayList<Observer> observers;
    private String state;
    private Inventory inventory;
    private KeyCode move;
    private int initX, initY;

    /**
     * Create a player positioned in square (x,y)
     * 
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        initX = x;
        initY = y;
        this.dungeon = dungeon;
        this.observers = new CopyOnWriteArrayList<>();
        this.state = "NORMAL";
        inventory = new Inventory();
    }

    public void initialize() {
        setX(initX);
        setY(initY);
    }

    public void moveUp() {
        if (!dungeon.checkClash(this, 0, -1)) {
            if (getY() > 0) {
                y().set(getY() - 1);
                move = KeyCode.UP;
            }
        }

    }

    public void moveDown() {
        if (!dungeon.checkClash(this, 0, 1)) {
            if (getY() < dungeon.getHeight() - 1) {
                y().set(getY() + 1);
                move = KeyCode.DOWN;
            }
        }
    }

    public void moveLeft() {
        if (!dungeon.checkClash(this, -1, 0)) {
            if (getX() > 0) {
                x().set(getX() - 1);
                move = KeyCode.LEFT;
            }
        }
    }

    public void moveRight() {
        if (!dungeon.checkClash(this, 1, 0)) {
            if (getX() < dungeon.getWidth() - 1) {
                x().set(getX() + 1);
                move = KeyCode.RIGHT;
            }
        }
    }

    public void useKey() {
        inventory.useKey();
    }

    public boolean useWeapon() {
        if (inventory.useWeapon(getX(), getY())) {
            return true;
        }
        return false;
    }

    public void useBomb() {
        inventory.useBomb(getX(), getY());
    }

    public void useBlade() {
        inventory.useBlade(getX(), getY(), move);
    }

    public int getKeyID() {
        return inventory.getKeyID();
    }

    public boolean updateInventory(Entity e) {
        return inventory.update(e);
    }

    @Override
    public void attach(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : this.observers) {
            o.update(this);
        }
    }

    public boolean playerDead() {
        return inventory.playerDead();
    }

    public void gameFinished() {
        dungeon.gameFinished();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }

    public void dropWeapon() {
        Entity weapon = inventory.removeWeapon();
        if (weapon != null) {
            weapon.setX(getX());
            weapon.setY(getY());
            dungeon.addEntity(weapon);
        }
    }

    public boolean jumpWall(Entity e, int x, int y, List<Entity> list) {
        if (!state.equals("JUMP"))
            return false;
        int tempX = getX() + 2 * x;
        int tempY = getY() + 2 * y;
        // Check the jump is within the bounds
        if (tempX > dungeon.getWidth() - 1 || tempX < 0 || (tempY > dungeon.getHeight() - 1) || tempY < 0)
            return false;

        for (Entity entity : list) {
            if (entity.getX() == tempX && entity.getY() == tempY) {
                return false;
            }
        }

        setX(e.getX());
        setY(e.getY());
        return true;
    }

    /**
     * Checks if a player clash can be handled
     * 
     * @param e
     * @param x
     * @param y
     * @return True if proper move
     */
    public boolean playerClash(Entity e, int x, int y, List<Entity> entities) {

        if (e instanceof Wall) {
            return jumpWall(e, x, y, entities);

        } else if (e instanceof Boulder) {
            return ((Boulder) e).pushBoulder(entities, x, y);

        } else if (e instanceof Portal) {

            Portal nextPortal = ((Portal) e).getPair(entities);
            if (nextPortal == null) {
                return false;
            }
            Entity clash = nextPortal.teleportEntity(entities, x, y);
            if (clash == null) {
                setX(nextPortal.getX());
                setY(nextPortal.getY());
                return true;
            } else if (playerClash(clash, x, y, entities)) {
                setX(nextPortal.getX());
                setY(nextPortal.getY());
                return true;
            }

        } else if (e instanceof Key || e instanceof Weapon || e instanceof Treasure || e instanceof Life) {
            if (updateInventory(e)) {
                dungeon.removeEntity(e);
                return true;
            }
        } else if (e instanceof Door) {
            return ((Door)e).checkDoor(this, x, y);

        } else if (e instanceof Enemy) {
            return ((Enemy)e).fightEnemy(this);

        } else if (e instanceof Potion) {
            if (e instanceof InvincibilityPotion) {
                setState("INVINCIBLE");
            } else if (e instanceof JumpPotion) {
                setState("JUMP");
            }

            ((Potion) e).activate(dungeon);
            return true;

        } else if (e instanceof ExitGate) {
            ((ExitGate)e).setStatus(true);
            dungeon.checkFinish();
            ((ExitGate)e).setStatus(false);
            dungeon.checkFinish();
        } else if (e instanceof Wizard) {
            return true;
        }
        return false;
    }

    public ArrayList<Integer> updateFX() {
        return this.inventory.updateFX();
    }

    /*public void setFX(int a , int b){
        this.inventory.setFX(a , b);
    }*/

}
