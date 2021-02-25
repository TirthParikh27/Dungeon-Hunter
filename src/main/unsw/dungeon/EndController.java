package unsw.dungeon;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class EndController {
    @FXML
    private Button again;
    
    @FXML
    private Button exit;

    private ChoiceScreen screen;

    @FXML
    public void handleAgain(){
        screen.start();

    }

    @FXML
    public void handleExit(){
        System.exit(0);
    }

    public void setScreen(ChoiceScreen choiceScreen) {
        this.screen = choiceScreen;
    }

}