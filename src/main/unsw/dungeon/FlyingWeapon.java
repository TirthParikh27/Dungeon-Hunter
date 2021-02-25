package unsw.dungeon;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.input.KeyCode;

public class FlyingWeapon extends Entity implements Weapon {
    protected Dungeon dungeon;
    protected KeyCode direction;
    protected Timeline timeline;

    public FlyingWeapon(int x, int y, Dungeon d) {
        super(x, y);
        dungeon = d;
        direction = KeyCode.RIGHT;
        timeline = new Timeline();
        EventHandler<ActionEvent> play = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                handleMovement();
            }
        };
        KeyFrame time = new KeyFrame(Duration.millis(100), play);
        timeline.getKeyFrames().add(time);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }

    @Override
    public Weapon useWeapon() {
        timeline.play();
        return null;
    }
    
    public void deactivate() {
        timeline.stop();
        setX(-5);
        setY(-5);
        dungeon.removeEntity(this);
    }

    public void moveUp() {
        if (!checkClash(0, -1)) {
            if (getY() > 0) {
                y().set(getY() - 1);
            }
        }
        
    }

    public void moveDown() {
        if (!checkClash(0, 1)) {
            if (getY() < dungeon.getHeight() - 1) {
                y().set(getY() + 1);
            }
        }
    }

    public void moveLeft() {
        if (!checkClash(-1, 0)) {
            if (getX() > 0) {
                x().set(getX() - 1);
            }
        }
    }

    public void moveRight() {
        if (!checkClash(1, 0)) {
            if (getX() < dungeon.getWidth() - 1) {
                x().set(getX() + 1);
            }
        }
    }

    public void handleMovement() {
        switch (direction) {
        case UP:
            moveUp();
            break;
        case DOWN:
            moveDown();
            break;
        case LEFT:
            moveLeft();
            break;
        case RIGHT:
            moveRight();
            break;

        default:
            break;
        }
    }

    public boolean checkClash(int x, int y) {
        int tempX = getX() + x;
        int tempY = getY() + y;
        if (tempX > dungeon.getWidth() - 1 || tempX < 0 || (tempY > dungeon.getHeight() - 1) || tempY < 0)
            deactivate();

        for (Entity entity : dungeon.getEntities()) {
            if (tempX == entity.getX() && tempY == entity.getY()) {
                if (entity instanceof Player) dungeon.gameFinished();
                if (entity instanceof Enemy) {
                    dungeon.removeEnemy((Enemy)entity);
                    deactivate();
                }
                if (entity instanceof Boulder || entity instanceof Wall) deactivate();
                if (entity instanceof Portal) {
                    Portal teleport = ((Portal)entity).getPair(dungeon.getEntities());
                    setX(teleport.getX());
                    setY(teleport.getY());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getCounter() {
        return 1;
    }

}