package unsw.dungeon;

import javafx.beans.property.BooleanProperty;

public interface Component {
	public boolean check();
	public boolean add(Component child);
	public boolean remove(Component child);
	public BooleanProperty getStatus();
	
}