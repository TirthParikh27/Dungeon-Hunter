package unsw.dungeon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class InfoController {
    @FXML
    private Button back;
    
    private StartScreen screen;

    @FXML
    public void handleBack(){
        screen.start();

    }


    public void setScreen(StartScreen startScreen) {
        this.screen = startScreen;
    }

}