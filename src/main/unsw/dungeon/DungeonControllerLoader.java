package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * 
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    private String filename;

    // Images
    private Image playerImage;
    private Image wallImage;
    private Image doorImage;
    private Image keyImage;
    private Image portalImage;
    private Image boulderImage;
    //
    private Image enemyImage;
    private Image exitImage;
    private Image invincibilityPotionImage;
    private Image switchImage;
    private Image treasureImage;
    private Image weaponImage;
    private Image jumpPotion;
    private Image bomb;
    private Image bladePic;
    private Image wizardImage;
    private Image archerImage;
    private Image arrowImage;
    private Image lyfeImage;
    private VBox vbox;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        this.filename = filename;
        entities = new ArrayList<>();
        playerImage = new Image((new File("src/main/unsw/dungeon/images/human_new.png")).toURI().toString());
        wallImage = new Image((new File("src/main/unsw/dungeon/images/brick_brown_0.png")).toURI().toString());
        doorImage = new Image((new File("src/main/unsw/dungeon/images/closed_door.png")).toURI().toString());
        keyImage = new Image((new File("src/main/unsw/dungeon/images/key.png")).toURI().toString());
        portalImage = new Image((new File("src/main/unsw/dungeon/images/portal.png")).toURI().toString());
        boulderImage = new Image((new File("src/main/unsw/dungeon/images/boulder.png")).toURI().toString());
        enemyImage = new Image((new File("src/main/unsw/dungeon/images/hound.png")).toURI().toString());
        exitImage = new Image((new File("src/main/unsw/dungeon/images/exit.png")).toURI().toString());
        invincibilityPotionImage = new Image(
                (new File("src/main/unsw/dungeon/images/brilliant_blue_new.png")).toURI().toString());
        switchImage = new Image((new File("src/main/unsw/dungeon/images/pressure_plate.png")).toURI().toString());
        treasureImage = new Image((new File("src/main/unsw/dungeon/images/gold_pile.png")).toURI().toString());
        weaponImage = new Image((new File("src/main/unsw/dungeon/images/greatsword_1_new.png")).toURI().toString());
        jumpPotion = new Image((new File("src/main/unsw/dungeon/images/bubbly.png")).toURI().toString());
        bomb = new Image((new File("src/main/unsw/dungeon/images/bomb-removebg-preview.png")).toURI().toString());
        bladePic = new Image((new File(
                "src/main/unsw/dungeon/images/depositphotos_5211837-stock-photo-circular-saw-blade-for-wood-removebg-preview.png"))
                        .toURI().toString());
        wizardImage = new Image((new File("src/main/unsw/dungeon/images/gnome.png")).toURI().toString());
        archerImage = new Image((new File("src/main/unsw/dungeon/images/deep_elf_master_archer.png")).toURI().toString());
        arrowImage = new Image((new File(
                "src/main/unsw/dungeon/images/images/star.png"))
                        .toURI().toString());
        lyfeImage = new Image((new File("src/main/unsw/dungeon/images/images/Heart.png")).toURI().toString());
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        door.setView(view);
        addEntity(door, view);
    }

    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Hound enemy) {
        ImageView view = new ImageView(enemyImage);
        addEntity(enemy, view);
    }

    @Override
    public void onLoad(ExitGate exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(InvincibilityPotion potion) {
        ImageView view = new ImageView(invincibilityPotionImage);
        addEntity(potion, view);
    }

    @Override
    public void onLoad(Switch button) {
        ImageView view = new ImageView(switchImage);
        addEntity(button, view);
    }

    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
    }

    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(weaponImage);
        addEntity(sword, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    public String getFilename() {
        return filename;
    }

    /**
     * Set a node in a GridPane to have its position track the position of an entity
     * in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the model
     * will automatically be reflected in the view.
     * 
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() < 0) {
                    node.setVisible(false);
                } else {
                    GridPane.setColumnIndex(node, newValue.intValue());
                    if (!node.isVisible())
                        node.setVisible(true);
                }

            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() < 0) {
                    node.setVisible(false);

                } else {
                    GridPane.setRowIndex(node, newValue.intValue());
                    if (!node.isVisible())
                        node.setVisible(true);
                }
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * 
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController(DungeonScreen screen) throws FileNotFoundException {
        return new DungeonController(load(), entities, screen);
    }

    @Override
    public void onLoad(JumpPotion potion) {
        ImageView view = new ImageView(jumpPotion);
        addEntity(potion, view);
    }

    @Override
    public void onLoad(Bomb bomb) {
        ImageView view = new ImageView(this.bomb);
        addEntity(bomb, view);

    }

    @Override
    public void onLoad(Blade blade) {
        ImageView view = new ImageView(bladePic);
        addEntity(blade, view);
    }

    @Override
    public void onLoad(Wizard wizard) {
        ImageView view = new ImageView(wizardImage);
        addEntity(wizard, view);
    }

    @Override
    public void onLoad(Archer archer) {
        ImageView view = new ImageView(archerImage);
        addEntity(archer, view);
    }

    @Override
    public void onLoad(Arrow arrow) {
        ImageView view = new ImageView(arrowImage);
        addEntity(arrow, view);
    }

    @Override
    public void onLoad(Life lyfe) {
        ImageView view = new ImageView(lyfeImage);
        addEntity(lyfe, view);
    }

    @Override
    public void onLoad(VBox vbox) {
        // TODO Auto-generated method stub
        this.vbox = vbox;
    }

    public VBox getVbox() {
        return vbox;
    }


}
