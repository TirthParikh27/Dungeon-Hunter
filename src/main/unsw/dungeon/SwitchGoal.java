package  unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class SwitchGoal implements Component{
    private String name;
    private BooleanProperty status;
    private Dungeon dungeon;

    public SwitchGoal(String name , Dungeon dungeon){
        super();
        this.name = name;
        this.status = new SimpleBooleanProperty();
        this.status.set(false);
        this.dungeon = dungeon;
    }


    @Override
    public boolean check() {
        this.status.set(this.dungeon.checkSwitch());
        return this.status.get();
    }

    @Override
    public boolean add(Component child) {
        return false;
        
    }

    @Override
    public boolean remove(Component child) {
        return false;
    }

    @Override
    public BooleanProperty getStatus() {
        // TODO Auto-generated method stub
        return this.status;
    }
}