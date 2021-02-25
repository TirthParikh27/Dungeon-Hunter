package unsw.dungeon;

public class Sword extends Entity implements Weapon{
	private int counter;
	public Sword(int x, int y) {
		super(x, y);
		this.counter = 5;
	}
	
	public int getCounter() {
		return counter;
	}

	@Override
	public Weapon useWeapon(){
		this.counter--;
		if (counter == 0) {
			return null;
		}
		return this;
	}
}