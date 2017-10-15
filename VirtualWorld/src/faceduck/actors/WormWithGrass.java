package faceduck.actors;

import faceduck.ai.WormAI;
import faceduck.skeleton.interfaces.Animal;
import faceduck.skeleton.interfaces.Command;
import faceduck.skeleton.interfaces.World;
import faceduck.skeleton.util.Direction;
import faceduck.skeleton.util.Location;

/**
 * This is special object. Worm is parasitic on grass.
 * So this object has both property of `Worm` and `Grass`.
 *
 * Rabbit can eat this object, but energy value is lower
 * than grass because of worm. (Similar with noise)
 *
 * And worm can move to adjacent grass.
 * Then original `WormWithGrass` object turns into `Grass` and
 * adjacent `Grass` object turns into `WormWithGrass`
 */
public class WormWithGrass extends Grass implements Animal {

	private static final int WORM_WITH_GRASS_MAX_ENERGY = 10;
	private static final int WORM_WITH_GRASS_ENERGY_VALUE = 4;
	private static final int WORM_WITH_GRASS_VIEW_RANGE = 1;
	private static final int WORM_WITH_GRASS_COOL_DOWN = 4;
	private static final int WORM_WITH_GRASS_BREED_LIMIT = 0;

	private WormAI ai;

	/**
	 * Constructor
	 */
	public WormWithGrass() {
		this.ai = new WormAI();
	}

	/**
	 * Rabbit's act of each step
	 *
	 * @param world
	 *            The world that the actor is currently in.
	 */
	@Override
	public void act(World world) {
		Command cmd = ai.act(world, this);

		if (cmd != null)
			cmd.execute(world, this);
	}


	/**
	 * Worm do not use `eat` function. (They are parasitic!)
	 *
	 * @param world
	 *            The world containing this actor.
	 * @param dir
	 *            The direction of the Actor to eat.
	 */
	@Override
	public void eat(World world, Direction dir) {
		// WormWithGrass cannot eat something. (They are parasitic!)
		return;
	}


	/**
	 * Moves the worm to another Grass (not with a worm) in the world in the specified direction.
	 * The target `Grass` would be changed to `WormWithGrass` and
	 * original `WormWithGrass` object would be changed to `Grass`
	 *
	 * @param world
	 *            The world containing this actor.
	 * @param dir
	 *            The direction to move in.
	 *
	 * @throws NullPointerException
	 *             If world or dir are null.
	 */
	@Override
	public void move(World world, Direction dir) {
		if (world == null) {
			throw new NullPointerException("World cannot be null");
		}
		if (dir == null) {
			throw new NullPointerException("Direction cannot be null");
		}

		Location currentLoc = world.getLocation(this);
		Location destination = new Location(currentLoc, dir);

		Object targetObject = world.getThing(destination);

		if (targetObject == null)
			return;
		else {
			world.remove(this);
			world.remove(targetObject);

			world.add(this, destination);
			world.add(targetObject, currentLoc);
		}
	}

	/**
	 * Worm do not use `breed` function. (They are parasitic!)
	 *
	 * @param world
	 *            The world containing this actor.
	 * @param dir
	 *            The direction of the Actor to eat.
	 */
	@Override
	public void breed(World world, Direction dir) {
		// WormWithGrass do not breed
		return;
	}


	@Override
	public int getEnergy() { return WORM_WITH_GRASS_BREED_LIMIT; }

	@Override
	public int getMaxEnergy() { return WORM_WITH_GRASS_MAX_ENERGY; }

	@Override
	public int getBreedLimit() { return WORM_WITH_GRASS_BREED_LIMIT; }

	@Override
	public int getEnergyValue() { return WORM_WITH_GRASS_ENERGY_VALUE; }

	@Override
	public int getViewRange() { return WORM_WITH_GRASS_VIEW_RANGE; }

	@Override
	public int getCoolDown() { return WORM_WITH_GRASS_COOL_DOWN; }

}
