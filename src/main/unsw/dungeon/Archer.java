package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.scene.input.KeyCode;


public class Archer extends Enemy{
    private List<KeyCode> direction;
    private Timer mytimer;
    private TimerTask task;
    private Arrow arrow;

	public Archer(int x, int y, Dungeon d, Arrow arrow) {
		super(x, y, d);
        direction = new CopyOnWriteArrayList<>();
        mytimer = new Timer();
        this.arrow = arrow;
    }
    
    @Override
    public void activate() {
        mytimer = new Timer();
        task = new TimerTask(){

            @Override
            public void run() {
                shootArrow();
            }
        };
        mytimer.scheduleAtFixedRate(task, 1600, 3000);
        timeline.play();
    }

    @Override
    public void deactivate() {
        timeline.stop();
        mytimer.cancel();
    }

    @Override
	public void moveEnemy(ArrayList<String> movement) {
        direction.clear();
        for (String string : movement) {
            switch (string) {
                case "UP":
                    if (!dungeon.checkClash(this, 0, -1)) {
                        moveUp();
                        direction.add(KeyCode.UP);
                    }
                    break;
                case "DOWN":
                    if (!dungeon.checkClash(this, 0, 1)) {
                        moveDown();
                        direction.add(KeyCode.DOWN);
                    }
                    break;
                case "LEFT":
                    if (!dungeon.checkClash(this, -1, 0)) {
                        moveLeft();
                        direction.add(KeyCode.LEFT);
                    }
                    break;
                case "RIGHT":
                    if (!dungeon.checkClash(this, 1, 0)) {
                        moveRight();
                        direction.add(KeyCode.RIGHT);
                    }
                    break;
                default:
                    break;
                }
        }
        movement.clear();
    }

    public void shootArrow() {
        if (state instanceof Coward || direction.size() == 0) {
            return;
        }
        arrow.setX(getX());
        arrow.setY(getY());
        arrow.setDirection(new CopyOnWriteArrayList<>(direction));
        arrow.useWeapon();
    }

}

