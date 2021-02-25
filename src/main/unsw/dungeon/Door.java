package unsw.dungeon;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Door extends Entity {
    private int id;
    private boolean status;
    ImageView view;
    public Door(int x , int y , int id){
        super(x, y);
        this.status = false;
        this.id = id;
    }

    public boolean getStatus(){
        return this.status;
    }

    public boolean tryOpen(int key){
        if (key == 0) return false;
        if (key == id) {
            this.status = true;
            this.view.setImage(new Image((new File("src/main/unsw/dungeon/images/open_door.png")).toURI().toString()));
            return true;
        } 

        return false;
    }

    public boolean checkDoor(Player player, int x, int y) {

        if(getStatus()) {

            player.setX(getX());
            player.setY(getY());
            return true;

        } else if (tryOpen(player.getKeyID())) {

            player.useKey();
            player.setX(getX());
            player.setY(getY());
            return true;
        }

        return false;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    
}