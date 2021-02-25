package unsw.dungeon;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.KeyCode;

public class Arrow extends FlyingWeapon{
    private List<KeyCode> directions;
    private int moveLength;
    private int moveStep;

    public Arrow(int x, int y, Dungeon d) {
        super(x, y, d);
        moveLength = moveStep = 0;
        directions = new ArrayList<>();
    }

    public void setDirection(List<KeyCode> movement) {
        directions = movement;
    }

    @Override
    public void handleMovement() {
        moveLength = directions.size();
        for (KeyCode keyCode : directions) {
            moveStep++;
            switch (keyCode) {
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
        moveStep = 0;
    }

    @Override
    public boolean checkClash(int x, int y) {
        if(moveStep != moveLength) return false;
        int tempX = getX() + x;
        int tempY = getY() + y;
        if (tempX > dungeon.getWidth() - 1 || tempX < 0 || (tempY > dungeon.getHeight() - 1) || tempY < 0)
            deactivate();

        for (Entity entity : dungeon.getEntities()) {
            if (tempX == entity.getX() && tempY == entity.getY()) {
                if (entity instanceof Player) { 
                    dungeon.gameFinished();
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


}