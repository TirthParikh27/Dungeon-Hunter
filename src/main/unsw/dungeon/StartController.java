package unsw.dungeon;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class StartController {
    @FXML
    private Button play;
    @FXML
    private Pane root;
    
    @FXML
    private Button exit;

    private ChoiceScreen screen;
    private infoClass infoScreen;

    @FXML
    public void handlePlay(){
        screen.start();

    }

    @FXML
    public void handleExit(){
        System.exit(0);
    }

    @FXML
    public void handleHow(){
        infoScreen.start();
    }

    public void setScreen(ChoiceScreen choiceScreen) {
        this.screen = choiceScreen;
    }

    public void setInfoScreen(infoClass infoClass){
        this.infoScreen = infoClass;
    }

    public void fadein(Parent root){
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1500), root);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }


}