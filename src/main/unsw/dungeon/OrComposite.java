package  unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;

public class OrComposite implements Component {
    private String name;
    private boolean status;

    ArrayList<Component> children = new ArrayList<Component>();

    public OrComposite(String name) {
        super();
        this.name = name;
        this.status = false;
    }

    @Override
    public boolean check() {
        for (Component c : this.children) {
            // this.status = (this.status || c.check());
            if (c.check()) {
                this.status = true;
            }
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