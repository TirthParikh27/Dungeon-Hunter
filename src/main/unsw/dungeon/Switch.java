package unsw.dungeon;

import java.util.concurrent.CopyOnWriteArrayList;

public class Switch extends Entity implements Subject {
    private boolean status;
    private CopyOnWriteArrayList<Observer> observers;

    public Switch(int x, int y) {
        super(x, y);
        status = false;
        observers = new CopyOnWriteArrayList<>();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        notifyObservers();
    }
    
    /**
     * Flip the switch if possible and move the boulder 
     * @param s
     * @param x
     * @param y
     * @param boulder
     * @return True for success
     */
    public boolean flipSwitch() {
        if (isStatus()) {
            return false;
        }
        setStatus(true);
        return true;
    }

    @Override
    public void attach(Observer o) {
        this.observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : this.observers){
            o.update(this);
        }
    }

}