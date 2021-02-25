package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;



/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private VBox vbox;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        vbox = new VBox(10);
    }

    public DungeonLoader(JSONObject json){
        this.json = json;
    }
    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");
        JSONObject jsonGoals = json.getJSONObject("goal-condition");
        Component goal = loadGoal(jsonGoals , dungeon);
        dungeon.setGoal(goal);
        onLoad(vbox);
        //dungeon.setVbox(vbox);
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
        }
        dungeon.initialise();
        return dungeon;
    }

    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id ;

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        case "door":
            id = json.getInt("id");
            Door door = new Door(x, y, id);
            onLoad(door);
            entity = door;
            break;
        case "key":
            id = json.getInt("id");
            Key key = new Key(x,y,id);
            onLoad(key);
            entity = key;
            break;
        case "portal":
            id = json.getInt("id");
            Portal portal = new Portal(x, y, id);
            onLoad(portal);
            entity = portal;
            break;
        case "boulder":
            Boulder boulder = new Boulder(x, y);
            onLoad(boulder);
            entity = boulder;
            break;
        case "enemy":
            Hound enemy = new Hound(x,y, dungeon);
            onLoad(enemy);
            entity = enemy;
            break;

        case "exit":
            ExitGate exit = new ExitGate(x,y);
            onLoad(exit);
            entity = exit;
            break;

        case "invincibility":
            InvincibilityPotion potion = new InvincibilityPotion(x,y);
            onLoad(potion);
            entity = potion;
            break;

        case "jump":
            JumpPotion potion2 = new JumpPotion(x,y);
            onLoad(potion2);
            entity = potion2;
            break;

        case "switch":
            Switch button = new Switch(x,y);
            onLoad(button);
            entity = button;
            break;

        case "treasure":
            Treasure treasure = new Treasure(x,y);
            onLoad(treasure);
            entity = treasure;
            break;

        case "sword":
            Sword sword = new Sword(x,y);
            onLoad(sword);
            entity = sword;
            break;

        case "bomb":
            Bomb bomb = new Bomb(x, y, dungeon);
            onLoad(bomb);
            entity = bomb;
            break;

        case "blade":
            Blade blade = new Blade(x, y, dungeon);
            onLoad(blade);
            entity = blade;
            break;

        case "wizard":
            String motion = json.getString("motion");
            int range = json.getInt("range");
            int speed = json.getInt("speed");
            Wizard wizard = new Wizard(x, y, motion, range, speed);
            onLoad(wizard);
            entity = wizard;
            break;

        case "archer":
            Arrow arrow = new Arrow(x, y, dungeon);
            Archer archer = new Archer(x, y, dungeon, arrow);
            entity = archer;
            onLoad(archer);
            onLoad(arrow);
            arrow.setX(-1);
            arrow.setY(-1);
            break;

        case "life":
            Life lyfe = new Life(x, y);
            onLoad(lyfe);
            entity = lyfe;
            break;
        }

        dungeon.addEntity(entity);
    }

    public Component loadGoal(JSONObject json , Dungeon dungeon){
        String str = json.getString("goal");
        switch(str){
            case "enemies":
                Component enemy = new EnemyGoal("enemies", dungeon);
                CheckBox checkBox = new CheckBox("Kill All Enemies");
                checkBox.selectedProperty().bind(enemy.getStatus());
                checkBox.setDisable(true);
                vbox.getChildren().add(checkBox);
                return enemy;
                
            case "treasure":
                Component treasure = new TreasureGoal("treasure", dungeon);
                CheckBox checkBox2 = new CheckBox("Collect All Treasure");
                checkBox2.selectedProperty().bind(treasure.getStatus());
                checkBox2.setDisable(true);
                vbox.getChildren().add(checkBox2);
                return treasure;
               
            case "exit":
                Component exit = new ExitGoal("exit", dungeon);
                CheckBox checkBox3 = new CheckBox("Reach Exit Gate");
                checkBox3.selectedProperty().bind(exit.getStatus());
                checkBox3.setDisable(true);
                vbox.getChildren().add(checkBox3);
                return exit;
                
            case "boulders":
                Component switch1 = new SwitchGoal("switch", dungeon);
                CheckBox checkBox4 = new CheckBox("Trigger All Switches");
                checkBox4.setDisable(true);
                checkBox4.selectedProperty().bind(switch1.getStatus());
                vbox.getChildren().add(checkBox4);
                return switch1;
            case "AND":
                Component and = new AndComposite("and");
                JSONArray subgoals = json.getJSONArray("subgoals");
                for(int  i = 0 ; i < subgoals.length() ; i++){
                    JSONObject obj = subgoals.getJSONObject(i);
                    and.add(loadGoal(obj, dungeon));
                }
                return and;
                
            case "OR":
                Component or = new OrComposite("or");
                JSONArray subgoals2 = json.getJSONArray("subgoals");
                for(int j = 0; j < subgoals2.length() ; j++){
                    JSONObject obj2 = subgoals2.getJSONObject(j);
                    or.add(loadGoal(obj2, dungeon));
                }
                return or;
                
            case "default":
                break;

        }
        return null;
    }
    /*public VBox getVbox() {
        return vbox;
    }*/

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Wall wall);
    public abstract void onLoad(Door door);
    public abstract void onLoad(Key key);
    public abstract void onLoad(Portal portal);
    public abstract void onLoad(Boulder boulder);
    public abstract void onLoad(Hound enemy);
    public abstract void onLoad(ExitGate exit);
    public abstract void onLoad(InvincibilityPotion potion);
    public abstract void onLoad(JumpPotion potion);
    public abstract void onLoad(Switch button);
    public abstract void onLoad(Treasure treasure);
    public abstract void onLoad(Sword sword);
    public abstract void onLoad(Bomb bomb);
    public abstract void onLoad(Blade blade);
    public abstract void onLoad(Wizard wizard);
    public abstract void onLoad(Archer archer);
    public abstract void onLoad(Arrow arrow);
    public abstract void onLoad(Life lyfe);
    public abstract void onLoad(VBox vbox);
}
