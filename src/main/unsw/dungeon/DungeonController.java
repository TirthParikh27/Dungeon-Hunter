package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;
    @FXML
    private Label lives;
    @FXML
    private Label timer;
    @FXML 
    private Label sword;
    @FXML 
    private Label bomb;
    @FXML 
    private Label blade;
    @FXML 
    private Label invincibility;
    @FXML 
    private Label jump;
    @FXML 
    private Label treasure;
    @FXML 
    private Label key;
    @FXML 
    BorderPane border;

    private DungeonScreen screen;
    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, DungeonScreen screen) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.screen = screen;
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image((new File("src/main/unsw/dungeon/images/dirt_0_new.png")).toURI().toString());
        RowConstraints row = new RowConstraints();

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {

            for (int y = 0; y < dungeon.getHeight(); y++) {
                row.setVgrow(Priority.ALWAYS);
                squares.getRowConstraints().add(row);
                ColumnConstraints col = new ColumnConstraints();
                col.setHgrow(Priority.ALWAYS);
                squares.getColumnConstraints().add(col);
                squares.add(new ImageView(ground), x, y);
            }
        }
        for (ImageView entity : initialEntities){
            squares.getChildren().add(entity);
        }
        
        border.setRight(this.screen.getVbox());
        
    }

    public void updateFX(){
        ArrayList<Integer> updateFX = this.player.updateFX();
        if(player.getState().equals("INVINCIBLE")){
            updateFX.set(4, 1);
            updateFX.set(5, 0);
        } 

        if(player.getState().equals("JUMP")){
            updateFX.set(4, 0);
            updateFX.set(5, 1);
        }

        if(player.getState().equals("NORMAL")){
            updateFX.set(4, 0);
            updateFX.set(5, 0);
        }
        treasure.setText(updateFX.get(0).toString());
        sword.setText(updateFX.get(1).toString());
        bomb.setText(updateFX.get(2).toString());
        blade.setText(updateFX.get(3).toString());
        invincibility.setText(updateFX.get(4).toString());
        jump.setText(updateFX.get(5).toString());
        key.setText(updateFX.get(6).toString());
        lives.setText(updateFX.get(7).toString());
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        //updateFX();
        switch (event.getCode()) {
            case UP:
                player.moveUp();
                break;
            case DOWN:
                player.moveDown();
                break;
            case LEFT:
                player.moveLeft();
                break;
            case RIGHT:
                player.moveRight();
                break;
            case B:
                player.useBomb();
                break;

            case K:
                player.useBlade();
                break;

            case D:
                player.dropWeapon();
                break;

            default:
                break;
        }
        updateFX();
    }

    public void setVictory(VictoryScreen victory) {
        this.dungeon.setVictory(victory);
    }

    public void setLose(LostScreen lose) {
        this.dungeon.setLose(lose);
    }

    @FXML
    public void handleRestart() throws IOException {
        this.screen.restart();
    }

    @FXML
    public void handlePause(){
        this.dungeon.closeTimers();
        Stage pauseStage = new Stage();
        VBox box = new VBox(25);
        Label heading =  new Label("Game Paused");
        Button mainMenu = new Button("Main Menu");
        Button resume = new Button("Resume");
        Button exit = new Button("Exit");
        EventHandler<ActionEvent> main =  new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                pauseStage.close();
                ((Stage)squares.getScene().getWindow()).close();
                Stage primaryStage = new Stage();
                DungeonApplication dungeonApplication = new DungeonApplication();
                try {
                    dungeonApplication.start(primaryStage);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        mainMenu.setOnAction(main);
        
        EventHandler<ActionEvent> exitGame =  new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                pauseStage.close();
                ((Stage)squares.getScene().getWindow()).close();
                System.exit(0);
            }
        };
        exit.setOnAction(exitGame);
        
        EventHandler<ActionEvent> resumeGame =  new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                pauseStage.close();
                dungeon.restartTimer();
                border.requestFocus();
            }
        };
        resume.setOnAction(resumeGame);

        box.getChildren().add(heading);
        box.getChildren().addAll(mainMenu , resume , exit);
        box.setAlignment(Pos.CENTER);
        Scene pauseScreen = new Scene(box, 200 ,200);
        /*this.screen.getStage().setTitle("Pause Screen");
        this.screen.getStage().setScene(pauseScreen);
        this.screen.getStage().show();*/
        pauseStage.setTitle("Pause Screen");
        pauseStage.setScene(pauseScreen);
        pauseStage.centerOnScreen();
        pauseStage.show();
    }

    
    
}

