package unsw.dungeon;

import java.util.List;

public class Portal extends Entity {
    private int id;

    public Portal(int x ,int y , int id){
        super(x, y);
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public boolean checkID(int id){
        if(id == this.id){
            return true;
        }
        return false;
    }

    /**
     * Check if teleportation is possible 
     * @param list
     * @param x
     * @param y
     * @return Entity if their is a clash 
     */
    public Entity teleportEntity(List<Entity> list, int x, int y) {
        for (Entity entity : list) {
            if (entityClash(entity, getX() + x, getY() + y)) {
                return entity;
            }
        }
        return null;
    }

    public Portal getPair(List<Entity> list) {
        for (Entity entity2 : list) {
            if (entity2 instanceof Portal && ((Portal)entity2).getId() == getId()  && !equals(entity2)) 
                return (Portal)entity2;
        }
        return null;
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