package unsw.dungeon;

public class ExitGate extends Entity {

	private boolean status;

	public ExitGate(int x, int y) {
		super(x, y);
		status = false;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
    
}