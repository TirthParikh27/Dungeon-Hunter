package unsw.dungeon;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LostScreen{
    private Stage stage;
    private String title;
    private EndController controller;
    private Scene scene;
    
    public LostScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Lost Screen";

        controller = new EndController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/LostScreen.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();

    }



    public void start() {
        fadein(scene.getRoot());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public EndController getController() {
        return controller;
    }

    public void fadein(Parent root){
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), root);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }
    


}