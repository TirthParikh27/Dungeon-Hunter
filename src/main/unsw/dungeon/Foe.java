package unsw.dungeon;

import java.util.ArrayList;

public interface Foe {
    public void activate();
    public void deactivate();
    public void moveEnemy(ArrayList<String> movement);
    public boolean enemyClash(Entity e);
}