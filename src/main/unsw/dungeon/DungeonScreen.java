package unsw.dungeon;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DungeonScreen {

    private Stage stage;
    private String title;
    private DungeonController controller;
    private DungeonControllerLoader controllerLoader;
    private VictoryScreen victory;
    private LostScreen lose;

    public DungeonScreen(Stage stage) throws IOException {
        this.stage = stage;

        title = "Dungeon Screen";
        stage.setTitle(title);

        /*
         * controller = new StartController(); FXMLLoader loader = new
         * FXMLLoader(getClass().getResource("DungeonView.fxml"));
         * loader.setController(controller);
         * 
         * // load into a Parent node called root Parent root = loader.load(); scene =
         * new Scene(root); root.requestFocus();
         */
    }

    public void start(DungeonControllerLoader dungeonLoader, VictoryScreen victory, LostScreen lose)
            throws IOException {
        this.controllerLoader = dungeonLoader;
        this.victory = victory;
        this.lose = lose;
        this.controller = this.controllerLoader.loadController(this);
        this.controller.setVictory(victory);
        this.controller.setLose(lose);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        fadein(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
    }

    public void restart() throws IOException {
        String filename = this.controllerLoader.getFilename();
        this.controllerLoader = new DungeonControllerLoader(filename);
        this.controller = this.controllerLoader.loadController(this);
        this.controller.setVictory(victory);
        this.controller.setLose(lose);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        System.out.println(root.getId());
        Scene scene = new Scene(root);
        fadein(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
    }
    public Stage getStage() {
        return stage;
    }
    public DungeonController getController() {
        return controller;
    }

    public VBox getVbox() {
        return  this.controllerLoader.getVbox();
    }
    public void fadein(Parent root){
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), root);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }
    

}