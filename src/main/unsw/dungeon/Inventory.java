package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;

public class Inventory{
    private List<Entity> treasure;
    private Key key;
    private Entity weapon;
    private Bomb bomb;
    private ArrayList<Integer> updateFX;
    private int lives;
  
    public Inventory(){
        this.treasure = new ArrayList<>();
        this.updateFX = new ArrayList<>();
        this.key = null;
        this.weapon = null;
        lives = 2;
    }

    public int getKeyID() {
        if(key == null)
            return 0;
        return key.getId();
    }

    public void useKey(){
        this.key = null;
    }
   
    public Entity removeWeapon() {
        Entity temp = weapon;
        weapon = null;
        return temp;
    }

    public boolean playerDead() {
        lives--;
        if (lives <= 0)
            return true;
        return false;
    }

    public boolean useWeapon(int x, int y) {
        if (weapon != null && weapon instanceof Sword) {
            weapon = (Entity)((Weapon)weapon).useWeapon();
            return true;
        }
        return false;
    }

    public void useBlade(int x, int y, KeyCode move) {
        if (weapon != null && weapon instanceof Blade) {
            weapon.setX(x);
            weapon.setY(y);
            ((Blade)weapon).setDirection(move);
            weapon = (Entity)((Weapon)weapon).useWeapon();
        }
    }

    public void useBomb(int x, int y) {
        if (bomb != null) {
            bomb.setX(x);
            bomb.setY(y);
            ((Weapon)bomb).useWeapon();
            bomb = null;
        }
    }
    public int getTreasure() {
        return treasure.size();
    }

    public int getHitsRemaining(){
        if(weapon != null && weapon instanceof Sword){
            return ((Weapon)weapon).getCounter();
        } else return 0;
    }

    public int getSword(){
        if(weapon instanceof Sword ){
            return 1;
        }
        return 0;
    }

    public int getLives() {
        return lives;
    }

    public int getBlade(){
        if(weapon instanceof Blade){
            return 1;
        }
        return 0;
    }

    public int getBomb(){
        if(bomb != null)return 1;
        return 0;
    }

    public ArrayList<Integer> updateFX(){
        updateFX.clear();
        updateFX.add(getTreasure());
        updateFX.add(getHitsRemaining());
        updateFX.add(getBomb());
        updateFX.add(getBlade());
        if(updateFX.size() == 4)updateFX.add(0);
        if(updateFX.size() == 5)updateFX.add(0);
        
        if(key != null){
            updateFX.add(1);
        } else {
            updateFX.add(0);
        }

        updateFX.add(getLives());
        
        return this.updateFX;
        
    }

    /*public void setFX(int a , int b){
        updateFX.set(a, b);
    }*/

    /**
     * Update inventory 
     * @param e
     * @return True if update accepted
     */
    public boolean update(Entity e) {
        
        if(e instanceof Treasure){
            treasure.add(e);
            return true;
        } else if(e instanceof Key && key == null){
            this.key = (Key)e;
            return true;
        } else if(e instanceof Bomb) {
            this.bomb = (Bomb)e;
            return true;
        } else if(e instanceof Weapon && weapon == null){
            this.weapon = e;
            return true;
        } else if (e instanceof Life) {
            lives++;
            return true;
        }
        return false;
    }
}