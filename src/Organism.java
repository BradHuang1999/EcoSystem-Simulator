/**
 * 
 */

/**
 * @author 348916626
 *
 */
abstract class Organism {

	/**
	 * 
	 */
	
	private int health;
	
	public int getHealth() {
		return health;
	}

	public void addHealth(int value) {
		this.health += value;
	}

	public Organism(int health) {
		this.health = health;
	}

}
