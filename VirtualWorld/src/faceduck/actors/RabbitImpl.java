package faceduck.actors;

import faceduck.ai.RabbitAI;
import faceduck.skeleton.interfaces.Edible;
import faceduck.skeleton.interfaces.Rabbit;
import faceduck.skeleton.interfaces.World;

public class RabbitImpl extends CommonAnimal implements Rabbit {
	private static final int RABBIT_MAX_ENERGY = 20;
	private static final int RABBIT_VIEW_RANGE = 3;
	private static final int RABBIT_BREED_LIMIT = RABBIT_MAX_ENERGY * 2 / 4;
	private static final int RABBIT_ENERGY_VALUE = 20;
	private static final int RABBIT_COOL_DOWN = 4;
	private static final int RABBIT_INITIAL_ENERGY = RABBIT_MAX_ENERGY * 1 / 2;


	private RabbitAI ai;

	/**
	 * Constructor
	 */
	public RabbitImpl() {
		this.energy = RABBIT_INITIAL_ENERGY;
		this.ai = new RabbitAI();
	}


	/**
	 * Rabbit's act of each step
	 *
	 * @param world
	 *            The world that the actor is currently in.
	 */
	@Override
	public void act(World world) {
		this.actWithAI(world, this.ai);
	}

	/**
	 * Returns possibility of eating this kind of object.
	 *
	 * @param prey
	 * 			   object to eat.
	 * @return True if prey is Grass, false otherwise.
	 */
	@Override
	public boolean edibleWith(Edible prey) {
		// Rabbit only eats Grass
		if (prey instanceof Grass)
			return true;
		else
			return false;
	}


	@Override
	public int getViewRange() { return RABBIT_VIEW_RANGE; }

	@Override
	public int getCoolDown() { return RABBIT_COOL_DOWN; }

	@Override
	public int getEnergyValue() { return RABBIT_ENERGY_VALUE; }

	@Override
	public int getMaxEnergy() { return RABBIT_MAX_ENERGY; }

	@Override
	public int getBreedLimit() { return RABBIT_BREED_LIMIT; }

}
