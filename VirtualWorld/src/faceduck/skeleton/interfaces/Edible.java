package faceduck.skeleton.interfaces;

/**
 * Defines a participant in the world that is able to be eaten. All edible
 * objects have an integer energy value.
 */
public interface Edible {

	/**
	 * Returns the Edible object's energy value. Energy values must be greater
	 * than 0.
	 *
	 * @return An integer representing the object's energy value.
	 */
	public int getEnergyValue();
}
