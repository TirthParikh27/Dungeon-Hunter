package unsw.dungeon;

import javafx.util.Duration;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Enemy extends Entity implements Observer, Foe, State {
	protected Dungeon dungeon;
	protected State brave;
	protected State coward;
    protected State state;
    protected Timeline timeline;

	public Enemy(int x, int y, Dungeon d) {
		super(x, y);
		dungeon = d;
		brave = new Brave(this);
		coward = new Coward(this);
        state = brave;
        timeline = new Timeline();
        EventHandler<ActionEvent> play = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                dungeon.getPlayer().notifyObservers();
            }
        };
        KeyFrame time = new KeyFrame(Duration.millis(1500), play);
        timeline.getKeyFrames().add(time);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public State getState() {
        return state;
    }
    
    @Override
    public void activate() {
        timeline.play();
    }

    @Override
    public void deactivate() {
        timeline.stop();
    }
	@Override
	public void checkAction(int x, int y) {
		state.checkAction(x, y);
	}

    @Override
	public void moveEnemy(ArrayList<String> movement) {
        for (String string : movement) {
            switch (string) {
                case "UP":
                    if (!dungeon.checkClash(this, 0, -1)) {
                        moveUp();
                    }
                    break;
                case "DOWN":
                    if (!dungeon.checkClash(this, 0, 1)) {
                        moveDown();
                    }
                    break;
                case "LEFT":
                    if (!dungeon.checkClash(this, -1, 0)) {
                        moveLeft();
                    }
                    break;
                case "RIGHT":
                    if (!dungeon.checkClash(this, 1, 0)) {
                        moveRight();
                    }
                    break;
                default:
                    break;
                }
        }
        movement.clear();
    }

	protected void moveUp() {
        if (getY() > 0)
            y().set(getY() - 1);
    }

    protected void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
    }

    protected void moveLeft() {
        if (getX() > 0)
            x().set(getX() - 1);
    }

    protected void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
	}
	

	@Override
	public void update(Subject obj) {
		Player p = (Player)obj;
		switch(p.getState()) {
			case "INVINCIBLE":
			state = coward;
            break;
            
            default:
            state = brave;
		}
        checkAction(p.getX(), p.getY());
	}

    /**
     * Checks for a clash between enemy and entity 
     * @param enemy
     * @param e
     * @return Return true if enemy can proceed
     */
    @Override
    public boolean enemyClash(Entity e) {
        if (e instanceof Wall){
            return false;
        }else if (e instanceof Boulder ) {
            return false;
        }else if (e instanceof Door) {
            if (((Door)e).getStatus()){
                setX(e.getX());
                setY(e.getY());
                return true;
            }
            return false;
        } else if (e instanceof Player) {
            return fightEnemy((Player)e);
        } else if (e instanceof Foe) {
            return false;
        }
        return true;
    }


    public boolean fightEnemy(Player player) {
        if((player.getState()).equals("INVINCIBLE")) {
            deactivate();
            dungeon.removeEnemy(this);
            return true;

        } else if(player.useWeapon()){   
            deactivate();                           
            dungeon.removeEnemy(this);
            return true;
        }
        if (!player.playerDead()) {
            player.initialize();
            return false;
        }
        dungeon.gameFinished();
        return false;
    }

}

