package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Wizard extends Entity{
    private String motion;
    private Timeline timeline;
    private Timer clashTimer;
    private int range;
    private int speed;

    public Wizard(int x, int y, String motion, int range, int speed) {
        super(x, y);
        this.motion = motion;
        timeline = new Timeline();
        this.range = range;
        clashTimer = new Timer();
        this.speed = speed;
    }
    public void activate(Player player) {
        clashTimer = new Timer();
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyValue kv1;
        if (motion.equals("vertical")) {
            kv1 = new KeyValue(y(), getY() + range, Interpolator.LINEAR);
        } else {
            kv1 = new KeyValue(x(), getX() + range, Interpolator.LINEAR);
        }
        KeyFrame kf1 = new KeyFrame(Duration.millis(speed), kv1);
        timeline.getKeyFrames().add(kf1);
        timeline.play();
        TimerTask timming = new TimerTask(){
            @Override
            public void run() {
                checkClash(player);
            }
        };
        clashTimer.scheduleAtFixedRate(timming, 0, 150);
    }

    public void checkClash(Player player) {
        if (getX() == player.getX() && getY() == player.getY() && !player.getState().equals("INVINCIBLE")) {
            player.gameFinished();
        }
    }

    public void deactivate() {
        timeline.stop();
        clashTimer.cancel();
    }
}