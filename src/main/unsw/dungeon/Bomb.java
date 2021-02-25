package unsw.dungeon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Bomb extends Entity implements Weapon {
    private Dungeon dungeon;
    private Timeline timeline;

    public Bomb(int x, int y, Dungeon d) {
        super(x, y);
        dungeon = d;
        timeline = new Timeline();
    }

    @Override
    public Weapon useWeapon() {
        EventHandler<ActionEvent> play = new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                deactivate();
            }
        };
        KeyFrame time = new KeyFrame(Duration.millis(3000), play);
        timeline.getKeyFrames().add(time);
        timeline.setCycleCount(1);
        timeline.play();
        
        return null;
    }

    public void deactivate() {
        for (int i = getX()-1; i < getX()+2; i++) {
            for (int j = getY()-1; j < getY()+2; j++) {
                if (i > dungeon.getWidth() - 1 || i < 0 || (j > dungeon.getHeight() - 1) || j < 0)
                    continue;
                for (Entity entity : dungeon.getEntities()) {
                    if (dungeon.entityClash(entity, i, j)) {
                        if (entity instanceof Enemy) dungeon.removeEnemy((Enemy)entity);
                        if (entity instanceof Wall) dungeon.removeEntity(entity);
                        if (entity instanceof Player) dungeon.gameFinished();
                    }
                }
            }
        }
        dungeon.removeEntity(this);
    }

    @Override
    public int getCounter() {
        return 1;
    }
    
}