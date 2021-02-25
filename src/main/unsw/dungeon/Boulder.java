package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity {

    public Boulder(int x, int y) {
        super(x, y);
    }
    
    public void moveBoulder(int x, int y) {
        y().set(getY() + y);
        x().set(getX() + x);
    }

    /**
     * Check if a boulder can be moved 
     * @param boulder
     * @param x
     * @param y
     * @param e
     * @return True if proper move 
     */
    private boolean boulderClash(Entity entity, int x, int y, List<Entity> list){
       
        if (entity instanceof Wall) {
            return false;

        }else if (entity instanceof Boulder){
            return false;

        } else if (entity instanceof Switch) {
            return ((Switch)entity).flipSwitch();

        } else if (entity instanceof Portal) {

            // Find the next portal 
            Portal nextPortal = ((Portal)entity).getPair(list);
            if (nextPortal == null) {
                return false;
            }

            Entity clash =  ((Portal)nextPortal).teleportEntity(list, x, y);
            if (clash == null) {
                setX(nextPortal.getX());
                setY(nextPortal.getY());
                return true;
            }else if (boulderClash(clash, x, y, list)) {
                setX(nextPortal.getX());
                setY(nextPortal.getY());
                return true;
            }

        }
        
        return false;
    }

    /**
     * Updates the position of a boulder if we can push it
     * @param e
     * @param x
     * @param y
     * @return True if pushed 
     */
    public boolean pushBoulder(List<Entity> list, int x, int y) {
        if (checkClash(list, x, y)) {
            return false;
        }
        flipCurrentSwitch(list, x, y);
        moveBoulder(x, y);
        return true;
    }

    /**
     * Check if a switch is present under the boulder 
     * @param e
     * @param x
     * @param y
     */
    private void flipCurrentSwitch(List<Entity> list, int x, int y) {
        for (Entity entity : list) {
            if (entity instanceof Switch && entityClash(entity, getX(), getY())) {
                ((Switch)entity).setStatus(false);
            }
        }
    }

    /**
     * Checks if their is a clash with a entity
     * @param x
     * @param y
     * @return True if the clash cannot be resolved 
     */
    private boolean checkClash(List<Entity> list, int x, int y) {
        for (Entity entity : list) {
            if (entityClash(entity, getX() + x, getY() + y)) {
                return !boulderClash(entity, x, y, list);
            }
        }
        return false;
    }

    /**
     * Checks if there is an entity present at the given co-ordinate 
     * @param e
     * @param x
     * @param y
     * @return true if entity present 
     */
    private boolean entityClash(Entity e, int x, int y) {
        if (e == null) {
            return false;
        }
        if (y == e.getY() && (x == e.getX())) {
            return true;
        }
        return false;
    }

}