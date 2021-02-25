package unsw.dungeon;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChoiceScreen{
    private Stage stage;
    private String title;
    private ChoiceController controller;
    private Scene scene;
    
    public ChoiceScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "Choice Screen";

        controller = new ChoiceController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/choiceScreen.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
    }



    public void start() {
        fadein(getRoot());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public ChoiceController getController() {
        return controller;
    }
    public Parent getRoot(){
        return scene.getRoot();
    }

    public void fadein(Parent root){
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), root);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }



}