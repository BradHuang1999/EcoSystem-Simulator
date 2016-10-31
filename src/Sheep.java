
public class Sheep extends Organism{
	
	private int gender;

	public Sheep(int health, int gender) {
		super(health);
		this.gender = gender;
	}
	
	public int getHealth() {
		return super.getHealth();
	}

	public void addHealth(int value) {
		super.addHealth(value);
	}
	
	public int getGender() {
		return this.gender;
	}
	
	public void eatGrass (Grass grass){
		this.addHealth(grass.getHealth());
	}
}
