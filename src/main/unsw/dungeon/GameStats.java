package unsw.dungeon;

public class GameStats implements Observer{
    private int treasures, switches, enemies;
    private Dungeon dungeon;

    public GameStats(int treasures, int switches, int enemies, Dungeon dungeon) {
        this.treasures = treasures;
        this.switches = switches;
        this.enemies = enemies;
        this.dungeon = dungeon;
    }
    
    public boolean enemiesDead() {
        return (enemies == 0)? true:false;
    }
    public boolean treasureCollected() {
        return (treasures == 0)? true:false;
    }
    public boolean switchesTrigged() {
        return (switches == 0)? true:false;
    }

    public void updateSwitch(int change){
        switches += change;
        //if(switchesTrigged())
        //Need to update checkboxes for goal display , so call everytime
        dungeon.checkFinish();
    }

    public void updateTreasure(int change){
        treasures += change;
        if(treasureCollected())
            dungeon.checkFinish();
    }

    public void updateEnemy(int change){
        enemies += change;
        if(enemiesDead())
            dungeon.checkFinish();
    }

    @Override
    public void update(Subject obj) {
        Switch s = (Switch)obj;
        if(s.isStatus()) {
            updateSwitch(-1);
        }else {
            updateSwitch(1);
        }
    }

}