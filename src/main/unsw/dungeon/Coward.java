package unsw.dungeon;

import java.util.ArrayList;

public class Coward implements State {
    private Foe enemy;

    public Coward(Foe e) {
        enemy = e;
    }

    @Override
    public void checkAction(int x, int y) {
        int diffX = ((Entity)enemy).getX() - x;
		int diffY = ((Entity)enemy).getY() - y;
		ArrayList<String> movement = new ArrayList<>();
		if (diffX < 0) {
			movement.add("LEFT");
		}
		if (diffY < 0) {
			movement.add("UP");
		}
		if (diffX > 0) {
			movement.add("RIGHT");
		} 
		if (diffY > 0) {
			movement.add("DOWN");
		}
		enemy.moveEnemy(movement);
    }
    
}