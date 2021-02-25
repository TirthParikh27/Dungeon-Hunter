package unsw.dungeon;

import java.util.TimerTask;

class TimeLimit extends TimerTask {

    private Dungeon dungeon;
    private Potion potion;
    public TimeLimit(Dungeon D, Potion p) {
        dungeon = D;
        potion = p;
    }

    public void run() {
        potion.deactivate();
        dungeon.normalPlayer();
    }
}
