package unsw.dungeon;

import java.util.Timer;

public class JumpPotion extends Entity implements Potion {

    private Timer mytimer;
    public JumpPotion(int x, int y) {
        super(x, y);
    }

    @Override
    public void activate(Dungeon dungeon) {
        dungeon.removeEntity((Entity)this);
        mytimer = new Timer();
		TimeLimit task = new TimeLimit(dungeon, this);
		mytimer.schedule(task, 10000);
    }

    @Override
    public void deactivate() {
        if (mytimer != null) {
			mytimer.cancel();
		}
    }
    
}