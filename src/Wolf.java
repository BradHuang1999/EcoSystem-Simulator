
public class Wolf extends Organism implements Comparable{

	private int gender;
	
	public Wolf(int health, int gender) {
		super(health);
		this.gender = gender;
	}
	
	public int getHealth() {
		return super.getHealth();
	}
	
	public int getGender() {
		return this.gender;
	}

	public void eatSheep (Sheep sheep){
		this.addHealth(sheep.getHealth());
	}
	
	public void fight (Wolf wolf){
		if (this.getHealth() < wolf.getHealth()) {
			this.addHealth(-10);
		} else if (this.getHealth() < wolf.getHealth()) {
			wolf.addHealth(-10);
		}
	}

}
