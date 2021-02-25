package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");

        StartScreen startScreen = new StartScreen(primaryStage);
        ChoiceScreen choiceScreen = new ChoiceScreen(primaryStage);
        DungeonScreen dungeonScreen = new DungeonScreen(primaryStage);
        VictoryScreen victory = new VictoryScreen(primaryStage);
        LostScreen lose = new LostScreen(primaryStage);
        infoClass info = new infoClass(primaryStage);
        info.getController().setScreen(startScreen);
        startScreen.getController().setScreen(choiceScreen);
        startScreen.getController().setInfoScreen(info);
        choiceScreen.getController().setScreen(dungeonScreen);
        victory.getController().setScreen(choiceScreen);
        lose.getController().setScreen(choiceScreen);
        choiceScreen.getController().setVictory(victory);
        choiceScreen.getController().setLost(lose);
        startScreen.getRoot().setOpacity(0);
        startScreen.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
