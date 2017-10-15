package faceduck.actors;

import faceduck.ai.GnatAI;
import faceduck.skeleton.interfaces.Animal;
import faceduck.skeleton.interfaces.Edible;
import faceduck.skeleton.interfaces.World;

/**
 * This is a simple implementation of a Gnat. It never loses energy and moves in
 * random directions.
 */
public class Gnat extends CommonAnimal implements Animal {
	private static final int GNAT_MAX_ENERGY = 10;
	private static final int GNAT_VIEW_RANGE = 1;
	private static final int GNAT_BREED_LIMIT = 0;
	private static final int GNAT_COOL_DOWN = 0;

	/**
	 * Constructor
	 */
	public Gnat() {
		this.energy = GNAT_MAX_ENERGY;
	}

	/**
	 * Gnat's act of each step
	 *
	 * @param world
	 *            The world that the actor is currently in.
	 *
	 */
	@Override
	public void act(World world) {
		this.actWithAI(world, new GnatAI());
	}

	/**
	 * Get consuming energy of each step.
	 *  And Gnat do not lose energy.
	 *
	 * @return Always 0
	 */
	@Override
	protected int getConsumingEnergy() {
		return 0;
	}

	/**
	 * Returns possibility of eating this kind of object.
	 *  And Gant do not eat anything.
	 *
	 * @param prey
	 * 			   object to eat.
	 * @return Always false.
	 */
	@Override
	public boolean edibleWith(Edible prey) {
		// Gnat cannot eat anything
		return false;
	}


	@Override
	public int getEnergy() { return GNAT_MAX_ENERGY; }

	@Override
	public int getViewRange() { return GNAT_VIEW_RANGE; }

	@Override
	public int getCoolDown() { return GNAT_COOL_DOWN; }

	@Override
	public int getMaxEnergy() { return GNAT_MAX_ENERGY; }

	@Override
	public int getBreedLimit() { return GNAT_BREED_LIMIT; }

}
