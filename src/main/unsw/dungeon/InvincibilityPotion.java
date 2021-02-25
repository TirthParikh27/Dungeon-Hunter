package unsw.dungeon;

import java.util.Timer;
public class InvincibilityPotion extends Entity implements Potion {

	private Timer mytimer;
	public InvincibilityPotion(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void activate(Dungeon dungeon) {
		dungeon.removeEntity((Entity)this);
		mytimer = new Timer();
		TimeLimit task = new TimeLimit(dungeon, this);
		mytimer.schedule(task, 5000);
	}

	@Override
	public void deactivate(){
		if (mytimer != null) {
			mytimer.cancel();
		}
	}
}


