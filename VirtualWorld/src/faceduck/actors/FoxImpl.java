package faceduck.actors;

import faceduck.ai.FoxAI;
import faceduck.skeleton.interfaces.Edible;
import faceduck.skeleton.interfaces.Fox;
import faceduck.skeleton.interfaces.Rabbit;
import faceduck.skeleton.interfaces.World;

public class FoxImpl extends CommonAnimal implements Fox {
	private static final int FOX_MAX_ENERGY = 160;
	private static final int FOX_VIEW_RANGE = 5;
	private static final int FOX_BREED_LIMIT = FOX_MAX_ENERGY * 3 / 4;
	private static final int FOX_COOL_DOWN = 2;
	private static final int FOX_INITIAL_ENERGY = FOX_MAX_ENERGY * 1 / 2;


	private FoxAI ai;

	/**
	 * Constructor
	 */
	public FoxImpl() {
		this.energy = FOX_INITIAL_ENERGY;
		this.ai = new FoxAI();
	}

	/**
	 * Fox's act of each step
	 * @param world
	 *            The world that the actor is currently in.
	 *
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
	 * @return True if prey is Rabbit, false otherwise.
	 */
	@Override
	public boolean edibleWith(Edible prey) {
		// Fox only eats Rabbit
		if (prey instanceof Rabbit)
			return true;
		else
			return false;
	}



	@Override
	public int getViewRange() { return FOX_VIEW_RANGE; }

	@Override
	public int getCoolDown() { return FOX_COOL_DOWN; }

	@Override
	public int getMaxEnergy() { return FOX_MAX_ENERGY; }

	@Override
	public int getBreedLimit() { return FOX_BREED_LIMIT; }
}
