/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.application.Platform;
import javafx.scene.layout.VBox;


/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Component goal;
    private GameStats stats;
    private VictoryScreen victory;
    private LostScreen lose;
    private List<Entity> stopTimers;
    private VBox vbox;


    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new CopyOnWriteArrayList<>();
        this.player = null;
        stopTimers = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void deleteEntity(Entity e) {
        entities.remove(e);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public Component getGoal() {
        return goal;
    }

    public void setVictory(VictoryScreen victory) {
        this.victory = victory;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setLose(LostScreen lose) {
        this.lose = lose;
    }

    public void setGoal(Component goal) {
        this.goal = goal;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        if (entity instanceof Treasure) {
            stats.updateTreasure(-1);
        }
        entity.setX(-5);
        entity.setY(-5);
        entities.remove(entity);

    }

    public boolean checkTreasure(){
        return stats.treasureCollected();
    }

    public boolean checkEnemy(){
        return stats.enemiesDead();
    }

    public boolean checkSwitch(){
        return stats.switchesTrigged();
    }

    public boolean checkExit(){
        for (Entity entity : entities) {
            if (entity instanceof ExitGate && ((ExitGate)entity).isStatus())
                return true;
        }
        return false;
    }
    /**
     * Checks if there is an entity present in the direction of moving entity 
     * @param e
     * @param x
     * @param y
     * @return True if their is a clash 
     */
    public boolean checkClash(Entity e, int x, int y) {
        for (Entity entity : entities) {

            if (entityClash(entity, e.getX() + x, e.getY() + y)) {
                // Player does not deal with a switch only boulder does
                if (entity instanceof Switch) continue;
                if (e instanceof Foe) {
                    return !((Foe)e).enemyClash(entity);
                /// Player clashing
                }  else if (e instanceof Player) {
                    return !player.playerClash(entity, x, y, entities);

                } 
            }
        }
        return false;
    }

    public void removeEnemy(Enemy enemy) {
        getPlayer().detach((Observer)enemy);
        removeEntity((Entity)enemy);
        stats.updateEnemy(-1);
    }

    public void closeTimers() {
        for (Entity entity : stopTimers) {
            if (entity instanceof Foe) {
                ((Foe)entity).deactivate();
            }else if (entity instanceof Potion) {
                ((Potion)entity).deactivate();
            }else if (entity instanceof Wizard) {
                ((Wizard)entity).deactivate();
            }
        }
    }

    public void restartTimer() {
        for (Entity entity : stopTimers) {
            if (entity instanceof Enemy) {
                ((Enemy)entity).activate();
            }else if (entity instanceof Wizard) {
                ((Wizard)entity).activate(getPlayer());
            }
        }
    }

    public void gameFinished() {
        if (!player.playerDead()) {
            player.initialize();
            return;
        }
        closeTimers();
        Platform.runLater(() -> {
            // anything that modifies the UI (/ scene)
            this.lose.start();
        });
    }
    
    void checkFinish(){
        if (goal == null)
            return;
        if(goal.check()){
            closeTimers();
            Platform.runLater(() -> {
                // anything that modifies the UI (/ scene)
                this.victory.start();
            });                                         
        }
    }

    public void normalPlayer() {
        getPlayer().setState("NORMAL");
    }
    

    /**
     * Checks if there is an entity present at the given co-ordinate 
     * @param e
     * @param x
     * @param y
     * @return true if entity present 
     */
    public boolean entityClash(Entity e, int x, int y) {
        if (e == null) {
            return false;
        }
        if (y == e.getY() && (x == e.getX())) {
            return true;
        }
        return false;
    }

    public void initialiseStats(int switches, int enemies, int treasures) {
        stats = new GameStats(treasures, switches, enemies, this);
        for (Entity entity : entities) {
            if (entity instanceof Switch) {
                ((Switch)entity).attach(stats);
            }
        }
    }

    public void initialise() {
        int treasures, switches, enemies;
        treasures = switches = enemies = 0;
        for (Entity entity : entities) {
            if (entity instanceof Switch) {
                switches += initialiseSwitch((Switch)entity);
            }else if (entity instanceof Foe) {
                ((Foe)entity).activate();
                getPlayer().attach((Observer)entity);
                stopTimers.add(entity);
                enemies++;
            }else if (entity instanceof Treasure) {
                treasures++;
            }else if (entity instanceof Wizard) {
                ((Wizard)entity).activate(getPlayer());
                stopTimers.add(entity);
            }else if (entity instanceof Potion) {
                stopTimers.add(entity);
            }
        }
        initialiseStats(switches, enemies, treasures);
    }

    public int initialiseSwitch(Switch s) {
        for (Entity e : entities) {
            if ((e instanceof Boulder) && (entityClash(e, s.getX(), s.getY()))) {
                s.setStatus(true);
                return 0;
            }
        }
        return 1;
    }
}



