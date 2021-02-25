package  unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;

public class AndComposite implements Component {
    private String name;
    private boolean status;

    ArrayList<Component> children = new ArrayList<Component>();

    public AndComposite(String name) {
        super();
        this.name = name;
        this.status = false;
    }

    @Override
    public boolean check() {
        int flag = 0;
        for (Component c : this.children) {
            if (!c.check()) {
                //this.status = false;
                flag = 1;
            }
            // System.out.println("final stat: "+ c.check());
        }
        if(flag == 0){
            this.status = true;
        } else {
            this.status = false;
        }
        
        return this.status;
    }

    @Override
    public boolean add(Component child) {
        return this.children.add(child);

    }

    @Override
    public boolean remove(Component child) {
        return this.children.remove(child);
    }

    @Override
    public BooleanProperty getStatus() {
        // TODO Auto-generated method stub
        return null;
    }
}