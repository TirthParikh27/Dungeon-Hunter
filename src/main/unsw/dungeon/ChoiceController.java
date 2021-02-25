package unsw.dungeon;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class ChoiceController {

    private DungeonScreen screen;
    private VictoryScreen victory;
    private LostScreen lose;
   
    @FXML
    public void handle1() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("2.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle2() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("1.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle3() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("3.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle4() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("test.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle5() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("4.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle6() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("5.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle7() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("6.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle8() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("7.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle9() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("8.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    @FXML
    public void handle10() throws IOException {
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("9.json");
        screen.start(dungeonLoader , this.victory , this.lose);
    }
    
    public void setScreen(DungeonScreen dungeonScreen) {
        this.screen = dungeonScreen;
    }

    public void setVictory(VictoryScreen victory){
        this.victory = victory;
    }

    public void setLost(LostScreen lose){
        this.lose = lose;
    }

    
}